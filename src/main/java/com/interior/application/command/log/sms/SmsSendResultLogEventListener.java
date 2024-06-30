package com.interior.application.command.log.sms;

import com.interior.application.command.log.sms.event.SmsSendResultLogEvent;
import com.interior.domain.sms.SmsSendResult;
import com.interior.domain.sms.repository.SmsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Async
@Slf4j
@Service
@RequiredArgsConstructor
public class SmsSendResultLogEventListener {

    private final SmsRepository smsRepository;

    @EventListener
    @Transactional
    public void createSmsSendResultLog(final SmsSendResultLogEvent event) {
        smsRepository.save(
                SmsSendResult.of(
                        event.senderPhoneNumber(),
                        event.to(),
                        event.msg(),
                        event.platformType()
                )
        );
    }
}
