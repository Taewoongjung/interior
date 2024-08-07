package com.interior.application.command.business.handlers;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.application.command.business.commands.UpdateBusinessProgressCommand;
import com.interior.domain.business.Business;
import com.interior.domain.business.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateBusinessProgressCommandHandler implements
        ICommandHandler<UpdateBusinessProgressCommand, Business> {

    private final BusinessRepository businessRepository;

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Business handle(final UpdateBusinessProgressCommand command) {
        log.info("execute UpdateBusinessProgressCommand");

        Business business = businessRepository.updateBusinessProgress(command.businessId(),
                command.progressType());

        log.info("UpdateBusinessProgressCommand executed successfully");

        return business;
    }
}
