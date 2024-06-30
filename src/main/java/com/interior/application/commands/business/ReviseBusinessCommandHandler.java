package com.interior.application.commands.business;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.application.commands.business.dto.ReviseBusinessCommand;
import com.interior.application.commands.log.business.dto.event.BusinessReviseLogEvent;
import com.interior.domain.business.Business;
import com.interior.domain.business.log.BusinessChangeFieldType;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.company.Company;
import com.interior.domain.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviseBusinessCommandHandler implements
        ICommandHandler<ReviseBusinessCommand, Boolean> {

    private final CompanyRepository companyRepository;
    private final BusinessRepository businessRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean handle(final ReviseBusinessCommand command) {
        log.info("execute ReviseBusinessCommand");

        Company company = companyRepository.findById(command.companyId());

        company.validateDuplicateName(command.changeBusinessName());

        Business business = businessRepository.findById(command.businessId());

        if (businessRepository.reviseBusiness(command.companyId(), command.businessId(),
                command.changeBusinessName())) {

            // 사업명 수정 로그
            eventPublisher.publishEvent(
                    new BusinessReviseLogEvent(
                            command.businessId(),
                            command.user().getId(),
                            BusinessChangeFieldType.REVISE_BUSINESS_NAME,
                            business.getName(),
                            command.changeBusinessName()
                    )
            );
        }

        log.info("ReviseBusinessCommand executed successfully");

        return true;
    }
}
