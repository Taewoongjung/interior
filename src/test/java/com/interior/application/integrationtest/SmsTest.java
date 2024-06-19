package com.interior.application.integrationtest;

import com.interior.adapter.outbound.sms.AligoSms.AligoSmsService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Disabled
@SpringBootTest
@ActiveProfiles(value = "dev")
public class SmsTest {

    @Autowired
    private AligoSmsService aligoSmsService;

    @Test
    void test1() throws Exception {
        aligoSmsService.sendSignUpVerificationSms("01088257754", "717177");
    }
}