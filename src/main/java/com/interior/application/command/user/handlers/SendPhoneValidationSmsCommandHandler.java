package com.interior.application.command.user.handlers;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.abstraction.serviceutill.IThirdPartyValidationCheckSender;
import com.interior.adapter.outbound.alarm.dto.event.ErrorAlarm;
import com.interior.application.command.user.commands.SendPhoneValidationSmsCommand;
import com.interior.application.command.util.sms.SmsUtilService;
import com.interior.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SendPhoneValidationSmsCommandHandler implements
        ICommandHandler<SendPhoneValidationSmsCommand, Void> {

    private final IThirdPartyValidationCheckSender smsThirdPartyValidationCheckSender;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    public SendPhoneValidationSmsCommandHandler(
            final SmsUtilService smsUtilService,
            final UserRepository userRepository,
            final ApplicationEventPublisher eventPublisher
    ) {
        this.smsThirdPartyValidationCheckSender = smsUtilService;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional
    public Void handle(final SendPhoneValidationSmsCommand command) {
        log.info("execute SendPhoneValidationSmsCommand");

        try {
            // 존재하는 휴대폰 번호 인지 검증
            userRepository.checkIfExistUserByPhoneNumber(command.targetPhoneNumber());

            smsThirdPartyValidationCheckSender.sendValidationCheck(command.targetPhoneNumber());

            log.info("SendPhoneValidationSmsCommand executed successfully");

        } catch (Exception e) {
            log.error("[Err_msg] SendPhoneValidationSmsCommand error occurred = {}", e.toString());

            eventPublisher.publishEvent(
                    new ErrorAlarm("SendPhoneValidationSmsCommand", e.toString()));
        }

        return null;
    }
}
