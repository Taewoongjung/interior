package com.interior.application.commands.business;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.application.commands.business.dto.DeleteBusinessMaterialCommand;
import com.interior.application.commands.log.business.material.dto.event.BusinessMaterialDeleteLogEvent;
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

        if (businessRepository.deleteBusinessMaterial(command.businessId(), command.materialId())) {

            // 사업 재료 삭제 로그
            eventPublisher.publishEvent(
                    new BusinessMaterialDeleteLogEvent(
                            command.businessId(),
                            command.materialId(),
                            command.user().getId()
                    ));
        }

        return true;
    }
}
