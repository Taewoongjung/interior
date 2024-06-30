package com.interior.application.commands.business;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.application.commands.business.dto.DeleteBusinessCommand;
import com.interior.application.commands.log.business.dto.event.BusinessDeleteLogEvent;
import com.interior.domain.business.Business;
import com.interior.domain.business.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteBusinessCommandHandler implements
        ICommandHandler<DeleteBusinessCommand, Boolean> {

    private final BusinessRepository businessRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean handle(final DeleteBusinessCommand command) {
        log.info("execute DeleteBusinessCommand");

        Business business = businessRepository.findById(command.businessId());

        if (businessRepository.deleteBusiness(command.companyId(), command.businessId())) {

            // 사업 삭제 로그
            eventPublisher.publishEvent(
                    new BusinessDeleteLogEvent(command.businessId(), command.user().getId(),
                            business.getName()));
        }

        log.info("DeleteBusinessCommand executed successfully");
        
        return true;
    }
}
