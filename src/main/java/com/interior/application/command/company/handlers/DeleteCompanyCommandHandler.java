package com.interior.application.command.company.handlers;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.application.command.company.commands.DeleteCompanyCommand;
import com.interior.domain.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteCompanyCommandHandler implements ICommandHandler<DeleteCompanyCommand, Boolean> {

    private final CompanyRepository companyRepository;

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean handle(final DeleteCompanyCommand command) {
        log.info("execute DeleteCompanyCommand = {}", command);

        companyRepository.delete(command.userId(), command.companyId());

        log.info("DeleteCompanyCommand executed successfully");

        return true;
    }
}
