package com.interior.adapter.outbound.sms;

public interface SmsService {

    void sendSignUpVerificationSms(
            final String to,
            final String verificationNumber
    ) throws Exception;
}
