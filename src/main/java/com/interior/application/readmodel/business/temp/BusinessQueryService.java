package com.interior.application.readmodel.business.temp;

import com.interior.adapter.outbound.cache.redis.excel.CacheExcelRedisRepository;
import com.interior.adapter.outbound.excel.BusinessListExcel;
import com.interior.adapter.outbound.excel.BusinessMaterialExcelDownload;
import com.interior.adapter.outbound.jpa.querydsl.BusinessDao;
import com.interior.application.readmodel.utill.sse.SseService;
import com.interior.domain.business.Business;
import com.interior.domain.business.material.log.BusinessMaterialLog;
import com.interior.domain.business.repository.BusinessRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessQueryService {

    private final SseService sseService;
    private final BusinessDao businessDao;
    private final BusinessRepository businessRepository;
    private final CacheExcelRedisRepository cacheExcelRedisRepository;


    @Transactional(readOnly = true)
    public Business getBusinessByCompanyIdAndBusinessId(final Long companyId,
            final Long businessId) {

        return businessRepository.findBusinessByCompanyIdAndBusinessId(companyId, businessId);
    }

    @Async
    @Transactional(readOnly = true)
    public void getExcelOfBusinessMaterialList(
            final Long companyId,
            final Long businessId,
            final String taskId,
            HttpServletResponse response
    ) {

        BusinessListExcel businessListExcel = null;

        Business business = getBusinessByCompanyIdAndBusinessId(companyId, businessId);

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + URLEncoder.encode("재료 리스트.xlsx", "UTF-8") + "\"");
            response.setContentType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            businessListExcel = BusinessListExcel.of(workbook);

            int dataSize = business.getBusinessMaterialList().size();

            cacheExcelRedisRepository.makeBucketByKey(taskId, dataSize);

            sseService.addEmitter(taskId);

            // 데이터 세팅
            businessListExcel.setData(business, cacheExcelRedisRepository, taskId,
                    sseService);

            ServletOutputStream outputStream = response.getOutputStream();
            businessListExcel.getWorkbook().write(outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public SseEmitter getExcelProgressInfo(final String taskId) {
        SseEmitter emitter = sseService.addEmitter(taskId);

        try {
            sseService.connect(taskId);
            sseService.streamData(taskId);
        } catch (Exception e) {
            emitter.completeWithError(e);
            throw new RuntimeException("Error during streaming data for task " + taskId, e);
        }

        return emitter;
    }

    @Transactional(readOnly = true)
    public List<BusinessMaterialExcelDownload> getExcelOfBusinessMaterialListV2(
            final Long companyId) {

        return businessDao.getBusinessMaterialList(companyId);
    }

    @Transactional(readOnly = true)
    public List<BusinessMaterialLog> getBusinessMaterialLog(final Long businessId) {

        List<BusinessMaterialLog> businessMaterialLogList = businessRepository.findBusinessMaterialLogByBusinessId(
                businessId);

        return businessMaterialLogList.stream()
                .sorted(Comparator.comparing(BusinessMaterialLog::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }
}
