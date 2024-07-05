package com.interior.application.command.user.handlers;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.abstraction.serviceutill.IThirdPartyValidationCheckSender;
import com.interior.adapter.outbound.alarm.dto.event.ErrorAlarm;
import com.interior.application.command.user.commands.SendEmailValidationMailCommand;
import com.interior.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SendEmailValidationMailCommandHandler implements
        ICommandHandler<SendEmailValidationMailCommand, Void> {

    private final IThirdPartyValidationCheckSender emailThirdPartyValidationCheckSender;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    public SendEmailValidationMailCommandHandler(
            @Qualifier("EmailIUtilService") final IThirdPartyValidationCheckSender emailService,
            final UserRepository userRepository,
            final ApplicationEventPublisher eventPublisher
    ) {
        this.emailThirdPartyValidationCheckSender = emailService;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Void handle(final SendEmailValidationMailCommand command) {
        log.info("execute SendEmailValidationMailCommand");

        try {
            // 존재하는 이메일인지 검증
            userRepository.checkIfExistUserByEmail(command.targetEmail());

            emailThirdPartyValidationCheckSender.sendValidationCheck(command.targetEmail());

            log.info("SendEmailValidationMailCommand executed successfully");

        } catch (Exception e) {
            log.error("[Err_msg] SendEmailValidationMailCommand error occurred = {}", e.toString());

            eventPublisher.publishEvent(
                    new ErrorAlarm("SendEmailValidationMailCommand", e.toString()));
        }

        return null;
    }
}
