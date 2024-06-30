package com.interior.adapter.inbound.company.command;

import com.interior.adapter.inbound.company.webdto.CreateCompanyWebDtoV1;
import com.interior.application.command.company.commands.CreateCompanyCommand;
import com.interior.application.command.company.commands.DeleteCompanyCommand;
import com.interior.application.command.company.handlers.CreateCompanyCommandHandler;
import com.interior.application.command.company.handlers.DeleteCompanyCommandHandler;
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

    private final CreateCompanyCommandHandler createCompanyCommandHandler;
    private final DeleteCompanyCommandHandler deleteCompanyCommandHandler;

    // 사업체 추가
    @PostMapping(value = "/api/companies")
    public ResponseEntity<Boolean> createCompany(
            @Valid @RequestBody final CreateCompanyWebDtoV1.Req req,
            @AuthenticationPrincipal final User user
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                createCompanyCommandHandler.handle(new CreateCompanyCommand(
                        user,
                        req.companyName(),
                        req.zipCode(),
                        req.mainAddress(),
                        req.subAddress(),
                        req.bdgNumber(),
                        req.tel()
                )));
    }

    // 특정 사업체 삭제
    @DeleteMapping(value = "/api/companies/{companyId}")
    public ResponseEntity<Boolean> deleteCompany(
            @PathVariable(value = "companyId") final Long companyId,
            @AuthenticationPrincipal final User user
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                deleteCompanyCommandHandler.handle(
                        new DeleteCompanyCommand(user.getId(), companyId))
        );
    }
}
