package com.interior.application.command.business.handlers;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.application.command.business.commands.DeleteBusinessMaterialCommand;
import com.interior.application.command.log.business.material.dto.event.BusinessMaterialDeleteLogEvent;
import com.interior.domain.business.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteBusinessMaterialCommandHandler implements
        ICommandHandler<DeleteBusinessMaterialCommand, Boolean> {

    private final BusinessRepository businessRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean handle(final DeleteBusinessMaterialCommand command) {
        log.info("execute DeleteBusinessMaterialCommand");

        if (businessRepository.deleteBusinessMaterial(command.businessId(), command.materialId())) {

            // 사업 재료 삭제 로그
            eventPublisher.publishEvent(
                    new BusinessMaterialDeleteLogEvent(
                            command.businessId(),
                            command.materialId(),
                            command.user().getId()
                    ));
        }

        log.info("DeleteBusinessMaterialCommand executed successfully");

        return true;
    }
}
