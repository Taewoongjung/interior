package com.interior.application.command.business.handlers;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.application.command.business.commands.ReviseUsageCategoryOfMaterialCommand;
import com.interior.domain.business.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviseUsageCategoryOfMaterialCommandHandler implements
        ICommandHandler<ReviseUsageCategoryOfMaterialCommand, Boolean> {

    private final BusinessRepository businessRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean handle(final ReviseUsageCategoryOfMaterialCommand command) {
        log.info("execute ReviseUsageCategoryOfMaterialCommand");

        businessRepository.reviseUsageCategoryOfMaterial(
                command.businessId(), command.targetList(), command.usageCategoryName());

        log.info("ReviseUsageCategoryOfMaterialCommand executed successfully");

        return true;
    }
}
