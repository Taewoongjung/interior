package com.interior.application.commands.business;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.application.commands.business.dto.CreateBusinessMaterialCommand;
import com.interior.application.commands.log.business.material.dto.event.BusinessMaterialCreateLogEvent;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.business.repository.dto.CreateBusinessMaterial;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateBusinessMaterialCommandHandler implements
        ICommandHandler<CreateBusinessMaterialCommand, Boolean> {

    private final BusinessRepository businessRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean handle(final CreateBusinessMaterialCommand command) {
        log.info("execute CreateBusinessCommand");

        // 재료 생성
        BusinessMaterial businessMaterial = businessRepository.save(
                new CreateBusinessMaterial(
                        command.businessId(),
                        command.req().name(),
                        command.req().usageCategory(),
                        command.req().category(),
                        command.req().amount(),
                        command.req().unit(),
                        command.req().memo(),
                        command.req().materialCostPerUnit(),
                        command.req().laborCostPerUnit()
                ));

        if (businessMaterial != null) {

            // 재료 생성에 대한 로그
            eventPublisher.publishEvent(
                    new BusinessMaterialCreateLogEvent(
                            command.businessId(),
                            businessMaterial.getId(),
                            command.user().getId(),
                            command.req().name()));
        }

        return true;
    }
}
