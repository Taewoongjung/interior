package com.interior.application.integrationtest;

import static java.lang.Thread.sleep;

import com.interior.application.command.user.UserCommandService;
import com.interior.application.command.util.sms.SmsUtilService;
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
    private UserCommandService userCommandService;

    @Autowired
    private SmsUtilService smsUtilService;

    @Test
    void test1() throws Exception {

        String[] aa = new String[4];

        aa[0] = "01063637786";
        aa[1] = "01088257754";
        aa[2] = "01047905567";
        aa[3] = "01090505567";

        for (int i = 0; i < 4; i++) {

            userCommandService.sendPhoneValidationSms(aa[i]);
            System.out.println(i + " 번째 {" + aa[i] + "}");
        }
        sleep(1000);
    }

    @Test
    void test2() throws Exception {

        String[] aa = new String[4];

        aa[0] = "01088257754";

        for (int i = 0; i < 1; i++) {

            userCommandService.sendPhoneValidationSms(aa[i]);
            System.out.println(i + " 번째 {" + aa[i] + "}");
        }

        sleep(1000);
    }

    @Test
    void test3() throws Exception {

        String[] aa = new String[4];

        aa[0] = "01088257754";

        for (int i = 0; i < 1; i++) {

            userCommandService.sendPhoneValidationSms(aa[i]);
            System.out.println(i + " 번째 {" + aa[i] + "}");
        }
    }
}