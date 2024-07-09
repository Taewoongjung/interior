package com.interior.application.command.user.handlers;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.adapter.outbound.cache.redis.dto.common.TearDownBucketByKey;
import com.interior.adapter.outbound.cache.redis.sms.CacheSmsValidationRedisRepository;
import com.interior.application.command.user.commands.ResetUserPasswordCommand;
import com.interior.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.interior.adapter.common.exception.ErrorType.NOT_VERIFIED_PHONE;
import static com.interior.util.CheckUtil.check;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResetUserPasswordCommandHandler implements
        ICommandHandler<ResetUserPasswordCommand, Boolean> {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CacheSmsValidationRedisRepository cacheSmsValidationRedisRepository;

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional
    public Boolean handle(final ResetUserPasswordCommand event) {
        log.info("execute ResetUserPasswordCommand = {}", event);
        
        check(!cacheSmsValidationRedisRepository.getIsVerifiedByKey(event.phoneNumber()), NOT_VERIFIED_PHONE);

        boolean isSuccess = userRepository.reviseUserPassword(
                event.email(), event.phoneNumber(), bCryptPasswordEncoder.encode(event.password()));

        if (isSuccess) {
            cacheSmsValidationRedisRepository.tearDownBucketByKey(new TearDownBucketByKey(event.phoneNumber()));

            log.info("ResetUserPasswordCommand executed successfully");

            return true;
        }

        return false;
    }
}
