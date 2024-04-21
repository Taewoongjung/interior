package com.interior.adapter.inbound.company;

import com.interior.adapter.inbound.company.webdto.CreateCompanyDto;
import com.interior.adapter.inbound.company.webdto.GetCompanyDto.GetCompanyResDto;
import com.interior.application.company.CompanyService;
import com.interior.application.company.dto.CreateCompanyServiceDto;
import com.interior.domain.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    // 특정 사업체 조회
    @GetMapping(value = "/api/companies/{companyId}")
    public ResponseEntity<GetCompanyResDto> getCompany(
            @AuthenticationPrincipal final User user,
            @PathVariable("companyId") final Long companyId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new GetCompanyResDto(
                                user.getName(),
                                companyService.getCompany(user.getEmail(), companyId))
                );
    }

    // 사업체 추가
    @PostMapping(value = "/api/companies")
    public ResponseEntity<Boolean> createCompany(
            @Valid @RequestBody final CreateCompanyDto.CreateCompanyReqDto req,
            @AuthenticationPrincipal final User user
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                companyService.createCompany(
                        user,
                        new CreateCompanyServiceDto.CreateCompanyDto(
                                req.companyName(),
                                req.zipCode(),
                                req.mainAddress(),
                                req.subAddress(),
                                req.bdgNumber(),
                                req.tel()
                        )
                )
        );
    }

    // 특정 사업체 삭제
    @DeleteMapping(value = "/api/companies/{companyId}")
    public ResponseEntity<Boolean> deleteCompany(
            @PathVariable(value = "companyId") final Long companyId,
            @AuthenticationPrincipal final User user
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                companyService.deleteCompany(user.getId(), companyId)
        );
    }
}
