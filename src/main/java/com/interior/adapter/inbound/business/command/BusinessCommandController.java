package com.interior.adapter.inbound.business.command;

import com.interior.adapter.inbound.business.webdto.CreateBusinessMaterial.CreateBusinessMaterialReqDto;
import com.interior.adapter.inbound.business.webdto.CreateBusinessWebDtoV1;
import com.interior.adapter.inbound.business.webdto.CreateBusinessWebDtoV1.Res;
import com.interior.adapter.inbound.business.webdto.ReviseBusiness;
import com.interior.adapter.inbound.business.webdto.ReviseBusinessMaterialWebDtoV1;
import com.interior.adapter.inbound.business.webdto.ReviseUsageCategoryOfMaterial;
import com.interior.adapter.inbound.business.webdto.SendQuotationDraftToClient;
import com.interior.adapter.inbound.business.webdto.UpdateBusinessProgressWebDtoV1;
import com.interior.application.command.business.commands.CreateBusinessCommand;
import com.interior.application.command.business.commands.CreateBusinessMaterialCommand;
import com.interior.application.command.business.commands.DeleteBusinessCommand;
import com.interior.application.command.business.commands.DeleteBusinessMaterialCommand;
import com.interior.application.command.business.commands.ReviseBusinessCommand;
import com.interior.application.command.business.commands.ReviseMaterialCommand;
import com.interior.application.command.business.commands.ReviseUsageCategoryOfMaterialCommand;
import com.interior.application.command.business.commands.SendQuotationDraftToClientCommand;
import com.interior.application.command.business.commands.UpdateBusinessProgressCommand;
import com.interior.application.command.business.handlers.CreateBusinessCommandHandler;
import com.interior.application.command.business.handlers.CreateBusinessMaterialCommandHandler;
import com.interior.application.command.business.handlers.DeleteBusinessCommandHandler;
import com.interior.application.command.business.handlers.DeleteBusinessMaterialCommandHandler;
import com.interior.application.command.business.handlers.ReviseBusinessCommandHandler;
import com.interior.application.command.business.handlers.ReviseMaterialCommandHandler;
import com.interior.application.command.business.handlers.ReviseUsageCategoryOfMaterialCommandHandler;
import com.interior.application.command.business.handlers.SendQuotationDraftToClientCommandHandler;
import com.interior.application.command.business.handlers.UpdateBusinessProgressCommandHandler;
import com.interior.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BusinessCommandController {

    private final CreateBusinessCommandHandler createBusinessCommandHandler;
    private final CreateBusinessMaterialCommandHandler createBusinessMaterialCommandHandler;
    private final DeleteBusinessMaterialCommandHandler deleteBusinessMaterialCommandHandler;
    private final DeleteBusinessCommandHandler deleteBusinessCommandHandler;
    private final ReviseBusinessCommandHandler reviseBusinessCommandHandler;
    private final ReviseUsageCategoryOfMaterialCommandHandler reviseUsageCategoryOfMaterialCommandHandler;
    private final ReviseMaterialCommandHandler reviseMaterialCommandHandler;
    private final UpdateBusinessProgressCommandHandler updateBusinessProgressCommandHandler;
    private final SendQuotationDraftToClientCommandHandler sendQuotationDraftToClientCommandHandler;

    // 사업 추가
    @PostMapping(value = "/api/companies/{companyId}/businesses")
    public ResponseEntity<Res> createBusiness(
            @PathVariable(value = "companyId") final Long companyId,
            @RequestBody final CreateBusinessWebDtoV1.Req req,
            @AuthenticationPrincipal final User user
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(
                new CreateBusinessWebDtoV1.Res(
                        true,
                        createBusinessCommandHandler.handle(
                                new CreateBusinessCommand(companyId, req, user))
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
                .body(createBusinessMaterialCommandHandler.handle(
                        new CreateBusinessMaterialCommand(
                                businessId,
                                req.materialName(),
                                req.materialUsageCategory(),
                                req.materialCategory(),
                                req.materialAmount(),
                                req.materialAmountUnit(),
                                req.materialMemo(),
                                req.materialCostPerUnit(),
                                req.laborCostPerUnit(),
                                user
                        )
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
                .body(deleteBusinessMaterialCommandHandler.handle(
                        new DeleteBusinessMaterialCommand(businessId, materialId, user)));
    }

    // 사업 삭제
    @DeleteMapping(value = "/api/companies/{companyId}/businesses/{businessId}")
    public ResponseEntity<Boolean> deleteBusiness(
            @PathVariable(value = "companyId") final Long companyId,
            @PathVariable(value = "businessId") final Long businessId,
            @AuthenticationPrincipal final User user
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(deleteBusinessCommandHandler.handle(
                        new DeleteBusinessCommand(companyId, businessId, user)));
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
                .body(reviseBusinessCommandHandler.handle(
                        new ReviseBusinessCommand(
                                companyId,
                                businessId,
                                req.changeBusinessName(),
                                user
                        ))
                );
    }


    // 특정 사업 재료의 사업 분류 수정
    @PatchMapping(value = "/api/businesses/{businessId}/categories/constructions")
    public ResponseEntity<Boolean> reviseUsageCategoryOfMaterial(
            @PathVariable(value = "businessId") final Long businessId,
            @RequestBody final ReviseUsageCategoryOfMaterial.Req req
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(reviseUsageCategoryOfMaterialCommandHandler.handle(
                        new ReviseUsageCategoryOfMaterialCommand(
                                businessId,
                                req.subDataIds(),
                                req.usageCategoryName()
                        ))
                );
    }

    // 특정 사업 재료 수정
    @PutMapping(value = "/api/businesses/{businessId}/materials/{materialId}")
    public ResponseEntity<Boolean> reviseMaterial(
            @PathVariable(value = "businessId") final Long businessId,
            @PathVariable(value = "materialId") final Long materialId,
            @RequestBody final ReviseBusinessMaterialWebDtoV1.Req req,
            @AuthenticationPrincipal final User user
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(reviseMaterialCommandHandler.handle(
                        new ReviseMaterialCommand(businessId, materialId, req,
                                user.getId()))
                );
    }


    // @TODO: 다른 사용자가 사업 단계 상태값을 악의적으로 변경할 가능성을 생각 해서 방안 찾아보기
    // 1. 관리자가 검수한다.
    // 2. ...
    @PatchMapping(value = "/api/businesses/{businessId}/progresses")
    public ResponseEntity<Boolean> updateBusinessProgress(
            @PathVariable(value = "businessId") final Long businessId,
            @RequestBody final UpdateBusinessProgressWebDtoV1.Req req
    ) {

        updateBusinessProgressCommandHandler.handle(
                new UpdateBusinessProgressCommand(businessId, req.progressType()));

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @PostMapping(value = "/api/businesses/{businessId}/quotations/draft/completions")
    public ResponseEntity<Boolean> sendQuotationDraftToClient(
            @PathVariable(value = "businessId") final Long businessId,
            @RequestBody SendQuotationDraftToClient.Req req
    ) {

        sendQuotationDraftToClientCommandHandler.handle(
                new SendQuotationDraftToClientCommand(businessId, req.receiverPhoneNumber()));

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}
