package com.interior.adapter.inbound.company;

import com.interior.adapter.inbound.company.webdto.CreateCompanyDto;
import com.interior.application.company.CompanyService;
import com.interior.application.company.dto.CreateCompanyServiceDto;
import com.interior.domain.company.Company;
import com.interior.domain.user.User;
import jakarta.validation.Valid;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping(value = "/api/companies")
    public ResponseEntity<List<Company>> getCompany(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompany(user));
    }

    @PostMapping(value = "/api/companies")
    public ResponseEntity<Boolean> createCompany(
            @Valid @RequestBody final CreateCompanyDto.CreateCompanyReqDto req,
            @AuthenticationPrincipal User user
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
