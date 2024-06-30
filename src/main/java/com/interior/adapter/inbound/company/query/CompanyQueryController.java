package com.interior.adapter.inbound.company.query;

import com.interior.adapter.inbound.company.webdto.GetCompanyDto.GetCompanyResDto;
import com.interior.application.readmodel.company.handlers.GetCompanyQueryHandler;
import com.interior.application.readmodel.company.queries.GetCompanyQuery;
import com.interior.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyQueryController {

    private final GetCompanyQueryHandler getCompanyQueryHandler;

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
                                getCompanyQueryHandler.handle(
                                        new GetCompanyQuery(user.getEmail(), companyId)))
                );
    }
}
