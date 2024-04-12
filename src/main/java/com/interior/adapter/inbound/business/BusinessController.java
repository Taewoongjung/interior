package com.interior.adapter.inbound.business;

import com.interior.adapter.inbound.business.webdto.CreateBusiness.CreateBusinessReqDto;
import com.interior.adapter.inbound.business.webdto.CreateBusinessMaterial.CreateBusinessMaterialReqDto;
import com.interior.application.businesss.BusinessService;
import com.interior.application.businesss.dto.CreateBusinessServiceDto.CreateBusinessMaterialDto;
import com.interior.domain.business.Business;
import com.interior.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessService businessService;

    @PostMapping(value = "/api/companies/{companyId}/businesses")
    public ResponseEntity<Boolean> createBusiness(
            @PathVariable(value = "companyId") final Long companyId,
            @RequestBody final CreateBusinessReqDto createBusinessReqDto
    ) {
        businessService.createBusiness(companyId, createBusinessReqDto);

        return ResponseEntity.status(HttpStatus.OK).body(true);
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

    @GetMapping(value = "/api/businesses/{businessId}")
    public ResponseEntity<Business> getBusiness(
            @PathVariable(value = "businessId") final Long businessId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(businessService.getBusiness(businessId));
    }

    @GetMapping(value = "/api/companies/{companyId}/businesses")
    public ResponseEntity<List<Business>> getBusinessByCompanyId(
            @AuthenticationPrincipal final User user,
            @PathVariable(value = "companyId") final Long companyId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(businessService.getBusinessesByCompanyId(companyId));
    }
}
