package com.interior.adapter.inbound.business;

import com.interior.adapter.inbound.business.webdto.CreateBusinessMaterial.CreateBusinessMaterialReqDto;
import com.interior.adapter.inbound.business.webdto.CreateBusinessWebDtoV1;
import com.interior.adapter.inbound.business.webdto.GetBusiness;
import com.interior.adapter.inbound.business.webdto.ReviseBusiness;
import com.interior.adapter.inbound.business.webdto.ReviseUsageCategoryOfMaterial;
import com.interior.application.command.business.BusinessCommandService;
import com.interior.application.command.business.dto.CreateBusinessServiceDto.CreateBusinessMaterialDto;
import com.interior.application.command.business.dto.ReviseBusinessServiceDto;
import com.interior.application.query.business.BusinessQueryService;
import com.interior.application.query.business.dto.GetBusinessMaterialLogs;
import com.interior.domain.business.Business;
import com.interior.domain.business.log.BusinessMaterialLog;
import com.interior.domain.company.Company;
import com.interior.domain.user.User;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessQueryService businessQueryService;
    private final BusinessCommandService businessCommandService;

    // 사업 추가
    @PostMapping(value = "/api/companies/{companyId}/businesses")
    public ResponseEntity<CreateBusinessWebDtoV1.Res> createBusiness(
            @PathVariable(value = "companyId") final Long companyId,
            @RequestBody final CreateBusinessWebDtoV1.Req req,
            @AuthenticationPrincipal final User user
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(
                new CreateBusinessWebDtoV1.Res(
                        true,
                        businessCommandService.createBusiness(companyId, req, user)
                ));
    }

    // 특정 사업에 재료 추가
    @PostMapping(value = "/api/businesses/{businessId}/materials")
    public ResponseEntity<Boolean> createBusinessMaterial(
            @PathVariable(value = "businessId") final Long businessId,
            @RequestBody final CreateBusinessMaterialReqDto req,
            @AuthenticationPrincipal final User user
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(businessCommandService.createBusinessMaterial(
                        businessId,
                        new CreateBusinessMaterialDto(
                                req.materialName(),
                                req.materialUsageCategory(),
                                req.materialCategory(),
                                req.materialAmount(),
                                req.materialAmountUnit(),
                                req.materialMemo(),
                                req.materialCostPerUnit(),
                                req.laborCostPerUnit()
                        ),
                        user
                ));
    }

    // 특정 사업에 포함 된 특정 재료 삭제
    @DeleteMapping(value = "/api/businesses/{businessId}/materials/{materialId}")
    public ResponseEntity<Boolean> deleteBusinessMaterial(
            @PathVariable(value = "businessId") final Long businessId,
            @PathVariable(value = "materialId") final Long materialId,
            @AuthenticationPrincipal final User user
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(businessCommandService.deleteBusinessMaterial(businessId, materialId, user));
    }

    // 특정 사업의 모든 재료 조회
    @GetMapping(value = "/api/businesses/{businessId}")
    public ResponseEntity<GetBusiness.Response> getBusiness(
            @PathVariable(value = "businessId") final Long businessId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(businessQueryService.getBusiness(businessId));
    }

    // 특정 사업 재료의 사업 분류 수정
    @PatchMapping(value = "/api/businesses/{businessId}")
    public ResponseEntity<Boolean> reviseUsageCategoryOfMaterial(
            @PathVariable(value = "businessId") final Long businessId,
            @RequestBody final ReviseUsageCategoryOfMaterial.Req req
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(businessCommandService.reviseUsageCategoryOfMaterial(
                        businessId,
                        req.subDataIds(),
                        req.usageCategoryName()
                ));
    }

    // 유저의 모든 사업들 조회
    @GetMapping(value = "/api/businesses")
    public ResponseEntity<List<Business>> getBusinessByUser(
            @AuthenticationPrincipal final User user
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(businessQueryService.getAllBusinessesByUser(
                        user.getCompanyList().stream()
                                .map(Company::getId)
                                .toList()));
    }

    // 회사의 사업들 조회
    @GetMapping(value = "/api/companies/{companyId}/businesses")
    public ResponseEntity<List<Business>> getBusinessByCompanyId(
            @PathVariable(value = "companyId") final Long companyId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(businessQueryService.getBusinessesByCompanyId(companyId));
    }

    // 사업 삭제
    @DeleteMapping(value = "/api/companies/{companyId}/businesses/{businessId}")
    public ResponseEntity<Boolean> deleteBusiness(
            @PathVariable(value = "companyId") final Long companyId,
            @PathVariable(value = "businessId") final Long businessId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(businessCommandService.deleteBusiness(companyId, businessId));
    }

    // 사업 수정
    @PatchMapping(value = "/api/companies/{companyId}/businesses/{businessId}")
    public ResponseEntity<Boolean> reviseBusiness(
            @PathVariable(value = "companyId") final Long companyId,
            @PathVariable(value = "businessId") final Long businessId,
            @RequestBody final ReviseBusiness.WebReqV1 req,
            @AuthenticationPrincipal final User user
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(businessCommandService.reviseBusiness(
                        companyId,
                        businessId,
                        new ReviseBusinessServiceDto.Req(
                                req.changeBusinessName()
                        ),
                        user
                ));
    }

    // 회사의 사업 리스트 엑셀 다운로드
    @GetMapping(value = "/api/excels/companies/{companyId}/businesses/{businessId}")
    public void getExcelOfBusinessMaterialList(
            @PathVariable(value = "companyId") final Long companyId,
            @PathVariable(value = "businessId") final Long businessId,
            @RequestParam("taskId") final String taskId,
            HttpServletResponse response
    ) {
        businessQueryService.getExcelOfBusinessMaterialList(companyId, businessId, taskId,
                response);
    }

    // 회사의 사업 리스트 엑셀 다운로드 프로그레스 조회
    @GetMapping(value = "/api/excels/tasks/{taskId}")
    public SseEmitter getExcelOfBusinessMaterialListProgressInfo(
            @PathVariable(value = "taskId") final String taskId
    ) throws IOException {
        return businessQueryService.getExcelProgressInfo(taskId);
    }

    // 재료 변경 로그 조회
    @GetMapping(value = "/api/businesses/{businessId}/logs")
    public ResponseEntity<List<GetBusinessMaterialLogs.Res>> getBusinessMaterialLogs(
            @PathVariable(value = "businessId") final Long businessId) {

        List<BusinessMaterialLog> logList = businessQueryService.getBusinessMaterialLog(businessId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(logList.stream()
                        .map(e ->
                                new GetBusinessMaterialLogs.Res(
                                        e.getUpdaterName(),
                                        e.getChangeField().getDesc(),
                                        e.getChangeDetail(),
                                        e.getCreatedAt()
                                )
                        ).collect(Collectors.toList()));
    }
}
