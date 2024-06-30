package com.interior.adapter.inbound.business.query;

import com.interior.adapter.inbound.business.enumtypes.QueryType;
import com.interior.adapter.inbound.business.webdto.GetBusinessMaterialLogsWebDtoV1;
import com.interior.adapter.inbound.business.webdto.GetBusinessWebDtoV1;
import com.interior.application.readmodel.business.handlers.GetAllBusinessesByUserQueryHandler;
import com.interior.application.readmodel.business.handlers.GetBusinessMaterialLogQueryHandler;
import com.interior.application.readmodel.business.handlers.GetBusinessQueryHandler;
import com.interior.application.readmodel.business.handlers.GetBusinessesByCompanyIdQueryHandler;
import com.interior.application.readmodel.business.handlers.GetExcelOfBusinessMaterialListQueryHandler;
import com.interior.application.readmodel.business.handlers.GetExcelProgressInfoQueryHandler;
import com.interior.application.readmodel.business.queries.GetAllBusinessesByUserQuery;
import com.interior.application.readmodel.business.queries.GetBusinessMaterialLogQuery;
import com.interior.application.readmodel.business.queries.GetBusinessQuery;
import com.interior.application.readmodel.business.queries.GetBusinessesByCompanyIdQuery;
import com.interior.application.readmodel.business.queries.GetExcelOfBusinessMaterialListQuery;
import com.interior.application.readmodel.business.queries.GetExcelProgressInfoQuery;
import com.interior.application.readmodel.business.temp.BusinessQueryService;
import com.interior.domain.business.Business;
import com.interior.domain.business.material.log.BusinessMaterialLog;
import com.interior.domain.company.Company;
import com.interior.domain.user.User;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class BusinessQueryController {

    private final BusinessQueryService businessQueryService;


    private final GetBusinessQueryHandler getBusinessQueryHandler;
    private final GetAllBusinessesByUserQueryHandler getAllBusinessesByUserQueryHandler;
    private final GetBusinessesByCompanyIdQueryHandler getBusinessesByCompanyIdQueryHandler;
    private final GetExcelOfBusinessMaterialListQueryHandler getExcelOfBusinessMaterialListQueryHandler;
    private final GetExcelProgressInfoQueryHandler getExcelProgressInfoQueryHandler;
    private final GetBusinessMaterialLogQueryHandler getBusinessMaterialLogQueryHandler;

    // 특정 사업의 모든 재료 조회
    @GetMapping(value = "/api/businesses/{businessId}")
    public ResponseEntity<GetBusinessWebDtoV1.Response> getBusiness(
            @PathVariable(value = "businessId") final Long businessId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(getBusinessQueryHandler.handle(new GetBusinessQuery(businessId)));
    }


    // 유저의 모든 사업들 조회
    @GetMapping(value = "/api/businesses")
    public ResponseEntity<List<Business>> getBusinessByUser(
            @AuthenticationPrincipal final User user
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(getAllBusinessesByUserQueryHandler.handle(
                        new GetAllBusinessesByUserQuery(
                                user.getCompanyList().stream()
                                        .map(Company::getId)
                                        .toList())
                ));
    }

    // 회사의 사업들 조회
    @GetMapping(value = "/api/companies/{companyId}/businesses")
    public ResponseEntity<List<Business>> getBusinessByCompanyId(
            @PathVariable(value = "companyId") final Long companyId,
            @RequestParam(value = "queryType", defaultValue = "none") final QueryType queryType
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(getBusinessesByCompanyIdQueryHandler.handle(
                        new GetBusinessesByCompanyIdQuery(companyId, queryType)));
    }

    // 회사의 사업 리스트 엑셀 다운로드
    @GetMapping(value = "/api/excels/companies/{companyId}/businesses/{businessId}")
    public void getExcelOfBusinessMaterialList(
            @PathVariable(value = "companyId") final Long companyId,
            @PathVariable(value = "businessId") final Long businessId,
            @RequestParam("taskId") final String taskId,
            HttpServletResponse response
    ) {
        getExcelOfBusinessMaterialListQueryHandler.handle(
                new GetExcelOfBusinessMaterialListQuery(companyId, businessId, taskId,
                        response));
    }

    // 회사의 사업 리스트 엑셀 다운로드 프로그레스 조회
    @GetMapping(value = "/api/excels/tasks/{taskId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter getExcelOfBusinessMaterialListProgressInfo(
            @PathVariable(value = "taskId") final String taskId
    ) {
        return getExcelProgressInfoQueryHandler.handle(new GetExcelProgressInfoQuery(taskId));
    }

    // 재료 변경 로그 조회
    @GetMapping(value = "/api/businesses/{businessId}/logs")
    public ResponseEntity<List<GetBusinessMaterialLogsWebDtoV1.Res>> getBusinessMaterialLogs(
            @PathVariable(value = "businessId") final Long businessId) {

        List<BusinessMaterialLog> logList = getBusinessMaterialLogQueryHandler.handle(
                new GetBusinessMaterialLogQuery(businessId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(logList.stream()
                        .map(e ->
                                new GetBusinessMaterialLogsWebDtoV1.Res(
                                        e.getUpdaterName(),
                                        e.getChangeField().getDesc(),
                                        e.getChangeDetail(),
                                        e.getCreatedAt()
                                )
                        ).collect(Collectors.toList()));
    }
}
