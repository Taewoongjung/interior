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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

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
                                req.mainAddress(),
                                req.subAddress(),
                                req.bdgNumber(),
                                req.tel()
                        )
                )
        );
    }
}
