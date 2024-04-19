package com.interior.adapter.inbound.business;

import com.interior.adapter.inbound.business.webdto.CreateBusiness.CreateBusinessReqDto;
import com.interior.adapter.inbound.business.webdto.CreateBusiness.CreateBusinessResDto;
import com.interior.adapter.inbound.business.webdto.CreateBusinessMaterial.CreateBusinessMaterialReqDto;
import com.interior.adapter.inbound.business.webdto.ReviseBusiness;
import com.interior.application.businesss.BusinessService;
import com.interior.application.businesss.dto.CreateBusinessServiceDto.CreateBusinessMaterialDto;
import com.interior.application.businesss.dto.ReviseBusinessServiceDto;
import com.interior.domain.business.Business;
import com.interior.domain.company.Company;
import com.interior.domain.user.User;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessService businessService;

    @PostMapping(value = "/api/companies/{companyId}/businesses")
    public ResponseEntity<CreateBusinessResDto> createBusiness(
            @PathVariable(value = "companyId") final Long companyId,
            @RequestBody final CreateBusinessReqDto createBusinessReqDto
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(
                new CreateBusinessResDto(
                        true,
                        businessService.createBusiness(companyId, createBusinessReqDto)
                ));
    }

    @PostMapping(value = "/api/businesses/{businessId}/materials")
    public ResponseEntity<Boolean> createBusinessMaterial(
            @PathVariable(value = "businessId") final Long businessId,
            @RequestBody final CreateBusinessMaterialReqDto req
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(businessService.createBusinessMaterial(
                        businessId,
                        new CreateBusinessMaterialDto(
                                req.materialName(),
                                req.materialCategory(),
                                req.materialAmount(),
                                req.materialMemo()
                        )
                ));
    }

    @DeleteMapping(value = "/api/businesses/{businessId}/materials/{materialId}")
    public ResponseEntity<Boolean> deleteBusinessMaterial(
            @PathVariable(value = "businessId") final Long businessId,
            @PathVariable(value = "materialId") final Long materialId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(businessService.deleteBusinessMaterial(businessId, materialId));
    }

    @GetMapping(value = "/api/businesses/{businessId}")
    public ResponseEntity<Business> getBusiness(
            @PathVariable(value = "businessId") final Long businessId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(businessService.getBusiness(businessId));
    }

    @GetMapping(value = "/api/businesses")
    public ResponseEntity<List<Business>> getBusinessByUser(
            @AuthenticationPrincipal final User user
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(businessService.getAllBusinessesByUser(
                        user.getCompanyList().stream()
                                .map(Company::getId)
                                .toList()));
    }

    @GetMapping(value = "/api/companies/{companyId}/businesses")
    public ResponseEntity<List<Business>> getBusinessByCompanyId(
            @PathVariable(value = "companyId") final Long companyId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(businessService.getBusinessesByCompanyId(companyId));
    }

    @DeleteMapping(value = "/api/companies/{companyId}/businesses/{businessId}")
    public ResponseEntity<Boolean> deleteBusiness(
            @PathVariable(value = "companyId") final Long companyId,
            @PathVariable(value = "businessId") final Long businessId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(businessService.deleteBusiness(companyId, businessId));
    }

    @PatchMapping(value = "/api/companies/{companyId}/businesses/{businessId}")
    public ResponseEntity<Boolean> reviseBusiness(
            @PathVariable(value = "companyId") final Long companyId,
            @PathVariable(value = "businessId") final Long businessId,
            @RequestBody final ReviseBusiness.WebReqV1 req
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(businessService.reviseBusiness(
                        companyId,
                        businessId,
                        new ReviseBusinessServiceDto.Req(
                                req.changeBusinessName()
                        )
                ));
    }
}
