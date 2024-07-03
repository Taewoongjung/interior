package com.interior.application.readmodel.business.handlers;

import com.interior.abstraction.domain.IQueryHandler;
import com.interior.adapter.outbound.cache.redis.excel.CacheExcelRedisRepository;
import com.interior.adapter.outbound.excel.BusinessListExcel;
import com.interior.application.readmodel.business.queries.GetExcelOfBusinessMaterialListQuery;
import com.interior.application.readmodel.utill.sse.SseService;
import com.interior.domain.business.Business;
import com.interior.domain.business.repository.BusinessRepository;
import jakarta.servlet.ServletOutputStream;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetExcelOfBusinessMaterialListQueryHandler implements
        IQueryHandler<GetExcelOfBusinessMaterialListQuery, CompletableFuture<Void>> {

    private final SseService sseService;
    private final BusinessRepository businessRepository;
    private final CacheExcelRedisRepository cacheExcelRedisRepository;

    @Override
    public boolean isQueryHandler() {
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<Void> handle(final GetExcelOfBusinessMaterialListQuery query) {
        log.info("process GetExcelOfBusinessMaterialListQuery {}", query);

        BusinessListExcel businessListExcel = null;

        Business business = businessRepository.findBusinessByCompanyIdAndBusinessId(
                query.getCompanyId(), query.getBusinessId());

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            query.setResponseHeader();

            businessListExcel = BusinessListExcel.of(workbook);

            int dataSize = business.getBusinessMaterialList().size();

            cacheExcelRedisRepository.makeBucketByKey(query.getTaskId(), dataSize);

            sseService.addEmitter(query.getTaskId());

            // 데이터 세팅
            businessListExcel.setData(business, cacheExcelRedisRepository, query.getTaskId(),
                    sseService);

            ServletOutputStream outputStream = query.getResponse().getOutputStream();
            businessListExcel.getWorkbook().write(outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
