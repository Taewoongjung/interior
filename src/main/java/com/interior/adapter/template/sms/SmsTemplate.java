package com.interior.adapter.template.sms;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_VALIDATION_NUMBER;
import static com.interior.util.CheckUtil.check;

import lombok.Getter;

@Getter
public class SmsTemplate {

    private final String textMessage;

    private SmsTemplate(final String textMessage) {
        this.textMessage = textMessage;
    }

    public static SmsTemplate phoneValidationTemplate(final String verificationNumber) {

        check(verificationNumber == null, EMPTY_VALIDATION_NUMBER);
        check("".equals(verificationNumber.trim()), EMPTY_VALIDATION_NUMBER);

        String msg =
                "인테리어 정가(鄭家) 입니다.\n"
                        + "인증번호 [" + verificationNumber + "]\n"
                        + "\"타인 노출 금지\"";

        return new SmsTemplate(msg);
    }
}
