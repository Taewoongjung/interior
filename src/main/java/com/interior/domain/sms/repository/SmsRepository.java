package com.interior.domain.sms.repository;

import com.interior.domain.sms.SmsSendResult;

public interface SmsRepository {

    void save(final SmsSendResult smsSendResult);
}
