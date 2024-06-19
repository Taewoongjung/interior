package com.interior.adapter.outbound.jpa.repository.sms;

import static com.interior.util.converter.jpa.sms.SmsEntityConverter.smsSendResultToEntity;

import com.interior.adapter.outbound.jpa.entity.sms.SmsSendResultEntity;
import com.interior.domain.sms.SmsSendResult;
import com.interior.domain.sms.repository.SmsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class SmsRepositoryAdapter implements SmsRepository {

    private final SmsJpaRepository smsJpaRepository;

    @Override
    @Transactional
    public void save(final SmsSendResult smsSendResult) {
        SmsSendResultEntity entity = smsSendResultToEntity(smsSendResult);
        smsJpaRepository.save(entity);
    }
}
