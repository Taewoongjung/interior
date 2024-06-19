package com.interior.adapter.outbound.sms.AligoSms;

import static com.interior.adapter.common.exception.ErrorType.UNABLE_TO_SEND_SMS;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interior.adapter.outbound.sms.SmsService;
import com.interior.adapter.template.sms.SmsTemplate;
import com.interior.application.command.log.sms.event.SmsSendResultLogEvent;
import com.interior.domain.sms.repository.SmsRepository;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AligoSmsService implements SmsService {

    private final WebClient webClient;
    private final SmsRepository smsRepository;
    private final ApplicationEventPublisher eventPublisher;

    private String senderPhoneNumber = "01088257754";


    @Value("${sms.aligo.key}")
    private String KEY;

    @Value("${sms.aligo.user-id}")
    private String USER_ID;


    @Async
    @Override
    @Transactional
    public void sendSignUpVerificationSms(
            final String to,
            final String verificationNumber
    ) throws Exception {

        SmsTemplate template = SmsTemplate.phoneValidationTemplate(verificationNumber);

        try {

            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("key", KEY);
            formData.add("user_id", USER_ID);
            formData.add("sender", senderPhoneNumber);
            formData.add("receiver", to);
            formData.add("msg", template.getTextMessage());

            Mono<String> responseMono = webClient.post()
                    .uri("https://apis.aligo.in/send/")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .bodyValue(formData)
                    .retrieve().bodyToMono(String.class);

            responseMono.subscribe(response -> {

                HashMap<String, String> parsedRes = parseSuccessString(response);

                if (parsedRes != null) {

                    eventPublisher.publishEvent(new SmsSendResultLogEvent(
                            senderPhoneNumber,
                            to,
                            parsedRes,
                            "ALIGO"
                    ));
                }
            }, error -> {
                log.error("휴대폰 ({}) 인증 sms 발송 실패 = {}", to, error.getMessage());

                eventPublisher.publishEvent(new SmsSendResultLogEvent(
                        senderPhoneNumber,
                        to,
                        null,
                        "ALIGO"
                ));
            });

        } catch (Exception e) {
            log.error("휴대폰 ({}) 인증 sms 발송 실패 = {}", to, e.getMessage());
            eventPublisher.publishEvent(new SmsSendResultLogEvent(
                    senderPhoneNumber,
                    to,
                    null,
                    "ALIGO"
            ));
            throw new Exception(UNABLE_TO_SEND_SMS.getMessage());
        }
    }

    private HashMap<String, String> parseSuccessString(final String target) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(target, new TypeReference<>() {
            });

        } catch (Exception e) {
            log.error("로그 생성 전 파싱 에러 = {}", e.getMessage());
        }

        return null;
    }
}
