package com.interior.adapter.outbound.sms;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface SmsService {

    void sendSignUpVerificationSms(
            final String from,
            final String to,
            final String verificationNumber
    ) throws UnirestException;
}
