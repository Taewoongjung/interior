package com.interior.adapter.inbound.company.command;

import com.interior.adapter.inbound.company.webdto.CreateCompanyDto;
import com.interior.application.command.company.CompanyCommandService;
import com.interior.application.command.company.dto.CreateCompanyServiceDto;
import com.interior.domain.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyCommandController {

    private final CompanyCommandService companyCommandService;


    // 사업체 추가
    @PostMapping(value = "/api/companies")
    public ResponseEntity<Boolean> createCompany(
            @Valid @RequestBody final CreateCompanyDto.CreateCompanyReqDto req,
            @AuthenticationPrincipal final User user
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                companyCommandService.createCompany(
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
                companyCommandService.deleteCompany(user.getId(), companyId)
        );
    }
}