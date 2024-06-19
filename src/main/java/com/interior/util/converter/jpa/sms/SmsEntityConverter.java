package com.interior.util.converter.jpa.sms;

import com.interior.adapter.outbound.jpa.entity.sms.SmsSendResultEntity;
import com.interior.domain.sms.SmsSendResult;

public class SmsEntityConverter {

    public static SmsSendResultEntity smsSendResultToEntity(final SmsSendResult smsSendResult) {
        return SmsSendResultEntity.of(
                smsSendResult.getIsSuccess(),
                smsSendResult.getType(),
                smsSendResult.getSender(),
                smsSendResult.getReceiver(),
                smsSendResult.getPlatformType(),
                smsSendResult.getResultCode(),
                smsSendResult.getMsgId()
        );
    }

}
