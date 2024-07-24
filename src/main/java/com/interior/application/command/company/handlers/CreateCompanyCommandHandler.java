package com.interior.application.command.company.handlers;

import static com.interior.adapter.common.exception.ErrorType.LIMIT_OF_COMPANY_COUNT_IS_FIVE;
import static com.interior.util.CheckUtil.check;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.adapter.outbound.alarm.dto.event.NewCompanyAlarm;
import com.interior.application.command.company.commands.CreateCompanyCommand;
import com.interior.domain.company.Company;
import com.interior.domain.company.repository.CompanyRepository;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateCompanyCommandHandler implements ICommandHandler<CreateCompanyCommand, Boolean> {

    private final CompanyRepository companyRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean handle(final CreateCompanyCommand command) {
        log.info("execute CreateCompanyCommand = {}", command);

        check(command.user().getCompanyList().size() >= 5, LIMIT_OF_COMPANY_COUNT_IS_FIVE);

        Company company = Company.of(
                command.companyName(),
                command.zipCode(),
                command.user().getId(),
                command.mainAddress(),
                command.subAddress(),
                command.bdgNumber(),
                command.tel(),
                BoolType.F,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        if (companyRepository.save(command.user().getEmail(), company)) {

            // 새로운 사업체 생성 시 알람 발송
            eventPublisher.publishEvent(
                    new NewCompanyAlarm(company.getName(),
                            command.user().getName(),
                            command.user().getEmail(),
                            company.getTel(),
                            company.getAddress() + " " + company.getSubAddress()
                    )
            );

            log.info("CreateCompanyCommand executed successfully");

            return true;
        }

        return false;
    }
}
