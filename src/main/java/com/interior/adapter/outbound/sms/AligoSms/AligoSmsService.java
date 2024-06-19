package com.interior.adapter.outbound.sms.AligoSms;

import static com.interior.adapter.common.exception.ErrorType.UNABLE_TO_SEND_SMS;

import com.interior.adapter.outbound.sms.SmsService;
import com.interior.adapter.template.sms.SmsTemplate;
import com.interior.domain.sms.SmsSendResult;
import com.interior.domain.sms.repository.SmsRepository;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class AligoSmsService implements SmsService {

    private final SmsRepository smsRepository;

    private String senderPhoneNumber = "01088257754";


    @Value("${sms.aligo.key}")
    private String KEY;

    @Value("${sms.aligo.user-id}")
    private String USER_ID;


    @Async
    @Override
    public void sendSignUpVerificationSms(
            final String to,
            final String verificationNumber
    ) throws Exception {

        SmsTemplate template = SmsTemplate.phoneValidationTemplate(verificationNumber);

        try {
            HttpResponse<JsonNode> response = Unirest.post("https://apis.aligo.in/send/")
                    .field("key", KEY)
                    .field("user_id", USER_ID)
                    .field("sender", senderPhoneNumber)
                    .field("receiver", to)
                    .field("msg", template.getTextMessage())
                    .asJson();

            smsRepository.save(
                    SmsSendResult.of(
                            senderPhoneNumber,
                            to,
                            response.getBody().getObject(),
                            "ALIGO"
                    )
            );

        } catch (Exception e) {
            log.error("휴대폰 ({}) 인증 sms 발송 실패 = {}", to, e.getMessage());
            throw new Exception(UNABLE_TO_SEND_SMS.getMessage());
        }
    }
}
