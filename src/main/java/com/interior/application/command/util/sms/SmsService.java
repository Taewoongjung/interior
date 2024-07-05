package com.interior.application.command.util.sms;

import com.interior.adapter.outbound.cache.redis.sms.CacheSmsValidationRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Async
@Slf4j
@Service
@RequiredArgsConstructor
public class SmsService {

    private final com.interior.adapter.outbound.sms.SmsService smsService;
    private final CacheSmsValidationRedisRepository cacheSmsValidationRedisRepository;

    @Transactional
    public void sendPhoneValidationSms(final String targetPhoneNumber) throws Exception {

        int validationNumber = createNumber();
        cacheSmsValidationRedisRepository.makeBucketByKey(targetPhoneNumber, validationNumber);

        smsService.sendSignUpVerificationSms(targetPhoneNumber, String.valueOf(validationNumber));
    }

    private int createNumber() {
        return (int) (Math.random() * (90000)) + 100000; //(int) Math.random() * (최댓값-최소값+1) + 최소값
    }
}
