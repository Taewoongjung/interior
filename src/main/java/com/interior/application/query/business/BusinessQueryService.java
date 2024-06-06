package com.interior.application.query.business;

import static java.util.stream.Collectors.groupingBy;

import com.interior.adapter.inbound.business.webdto.GetBusiness;
import com.interior.adapter.outbound.cache.redis.excel.CacheExcelRedisRepository;
import com.interior.adapter.outbound.emitter.EmitterRepository;
import com.interior.adapter.outbound.excel.BusinessListExcel;
import com.interior.adapter.outbound.excel.BusinessMaterialExcelDownload;
import com.interior.adapter.outbound.jpa.querydsl.BusinessDao;
import com.interior.domain.business.Business;
import com.interior.domain.business.log.BusinessMaterialLog;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.business.repository.BusinessRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    private final BusinessDao businessDao;
    private final EmitterRepository emitterRepository;
    private final BusinessRepository businessRepository;
    private final CacheExcelRedisRepository cacheExcelRedisRepository;

    private static final long DEFAULT_TIMEOUT = 86400000;


    @Transactional(readOnly = true)
    public GetBusiness.Response getBusiness(final Long businessId) {

        Business business = getBusinessByBusinessId(businessId);

        int count = 0;

        HashMap<String, List<BusinessMaterial>> businessMaterials = new HashMap<>();

        if (business.getBusinessMaterialList() != null) {

            businessMaterials =
                    (HashMap<String, List<BusinessMaterial>>)
                            business.getBusinessMaterialList().stream()
                                    .collect(groupingBy(BusinessMaterial::getUsageCategory));

            count = business.getBusinessMaterialList().size();
        }

        return new GetBusiness.Response(business.getName(), businessMaterials, count);
    }

    private Business getBusinessByBusinessId(final Long businessId) {
        return businessRepository.findById(businessId);
    }

    @Transactional(readOnly = true)
    public List<Business> getAllBusinessesByUser(final List<Long> companyIdList) {
        return businessRepository.findAllByCompanyIdIn(companyIdList);
    }

    @Transactional(readOnly = true)
    public List<Business> getBusinessesByCompanyId(final Long companyId) {
        return businessRepository.findBusinessByCompanyId(companyId);
    }

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

            // 데이터 세팅
            businessListExcel.setData(business, cacheExcelRedisRepository, taskId,
                    emitterRepository);

            ServletOutputStream outputStream = response.getOutputStream();
            businessListExcel.getWorkbook().write(outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public SseEmitter getExcelProgressInfo(final String taskId) throws IOException {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(taskId, emitter);

        emitter.onCompletion(() -> emitterRepository.deleteById(taskId));
        emitter.onTimeout(() -> {
            emitterRepository.deleteById(taskId);
            try {
                emitter.send(SseEmitter.event().id(taskId).data("Connection timed out"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                Map<String, String> progressInfo = cacheExcelRedisRepository.getBucketByKey(taskId);
                emitter.send(SseEmitter.event().id(taskId).data(progressInfo));
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

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
