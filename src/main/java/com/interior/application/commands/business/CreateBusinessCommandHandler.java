package com.interior.application.commands.business;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.adapter.outbound.alarm.dto.event.NewBusinessAlarm;
import com.interior.application.commands.business.dto.CreateBusinessCommand;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.business.repository.dto.CreateBusiness;
import com.interior.domain.company.Company;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateBusinessCommandHandler implements ICommandHandler<CreateBusinessCommand, Long> {

    private final BusinessRepository businessRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long handle(final CreateBusinessCommand command) {
        log.info("execute CreateBusinessCommand");

        final Long createdBusinessId = businessRepository.save(
                new CreateBusiness(
                        command.req().businessName(),
                        command.companyId(),
                        null,
                        command.req().zipCode(),
                        command.req().mainAddress(),
                        command.req().subAddress(),
                        command.req().bdgNumber()
                )
        );

        String companyName = command.user().getCompanyList()
                .stream()
                .filter(f -> command.companyId().equals(f.getId()))
                .findFirst().map(Company::getName)
                .orElseThrow(() -> new NoSuchElementException("사업체를 찾을 수 없습니다."));

        String businessAddress =
                "[" + command.req().zipCode() + "] " + command.req().mainAddress() + " "
                        + command.req().subAddress();

        // 새로운 사업 생성 시 알람 발송
        eventPublisher.publishEvent(
                new NewBusinessAlarm(command.req().businessName(),
                        companyName,
                        command.user().getName(),
                        command.user().getEmail(),
                        command.user().getTel(),
                        businessAddress,
                        command.req().bdgNumber()
                )
        );

        log.info("CreateBusinessCommand executed successfully");

        return createdBusinessId;
    }
}
