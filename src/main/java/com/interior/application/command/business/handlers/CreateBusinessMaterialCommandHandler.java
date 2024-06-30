package com.interior.application.command.business.handlers;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.application.command.business.commands.CreateBusinessMaterialCommand;
import com.interior.application.command.log.business.material.dto.event.BusinessMaterialCreateLogEvent;
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
                        command.materialName(),
                        command.materialUsageCategory(),
                        command.materialCategory(),
                        command.materialAmount(),
                        command.unitOfMaterialAmount(),
                        command.materialMemo(),
                        command.materialCostPerUnit(),
                        command.laborCostPerUnit()
                ));

        if (businessMaterial != null) {

            // 재료 생성에 대한 로그
            eventPublisher.publishEvent(
                    new BusinessMaterialCreateLogEvent(
                            command.businessId(),
                            businessMaterial.getId(),
                            command.user().getId(),
                            command.materialName()));
        }

        log.info("CreateBusinessCommand executed successfully");

        return true;
    }
}
