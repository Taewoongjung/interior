package com.interior.adapter.outbound.sms.AligoSms;

import com.interior.adapter.outbound.sms.SmsService;
import com.interior.domain.sms.SmsSendResult;
import com.interior.domain.sms.repository.SmsRepository;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class AligoSmsService implements SmsService {

    private final SmsRepository smsRepository;

    @Async
    @Override
    public void sendSignUpVerificationSms(
            final String from,
            final String to,
            final String verificationNumber
    ) throws UnirestException {

        String msg =
                "인테리어 정가(鄭家) 입니다.\n"
                        + "인증번호 [" + verificationNumber + "]\n"
                        + "\"타인 노출 금지\"";

        HttpResponse<JsonNode> response = Unirest.post("https://apis.aligo.in/send/")
                .field("key", "2kejb4jc0rbzxvcqfg8442nyp929ca7a")
                .field("user_id", "aipooh8882")
                .field("sender", from)
                .field("receiver", to)
                .field("msg", msg)
                .asJson();

        System.out.println("response = " + response);
        System.out.println("toString = " + response.toString());
        System.out.println("getBody = " + response.getBody().getObject());
        /*
            response = com.mashape.unirest.http.HttpResponse@3c36c9f4
            toString = com.mashape.unirest.http.HttpResponse@3c36c9f4
            getBody = {"success_cnt":1,"msg_type":"SMS","result_code":"1","error_cnt":0,"message":"success","msg_id":"825643758"}
            getCode = 200
            getHeaders = {date=Tue, 18 Jun 2024 10:31:08 GMT, server=Aligo Frontend Proxy, transfer-encoding=chunked, content-type=text/html; charset=UTF-8, connection=keep-alive}
        * */

        smsRepository.save(SmsSendResult.of(from, to, response.getBody().getObject(), "ALIGO"));
    }
}
