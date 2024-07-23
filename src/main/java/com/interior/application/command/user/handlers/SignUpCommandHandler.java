package com.interior.application.command.user.handlers;

import static com.interior.adapter.common.exception.ErrorType.NOT_VERIFIED_PHONE;
import static com.interior.util.CheckUtil.check;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.adapter.outbound.alarm.dto.event.NewUserAlarm;
import com.interior.adapter.outbound.alimtalk.dto.SendAlimTalk;
import com.interior.adapter.outbound.cache.redis.dto.common.TearDownBucketByKey;
import com.interior.adapter.outbound.cache.redis.sms.CacheSmsValidationRedisRepository;
import com.interior.application.command.user.commands.SignUpCommand;
import com.interior.domain.alimtalk.kakaomsgtemplate.KakaoMsgTemplateType;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignUpCommandHandler implements ICommandHandler<SignUpCommand, String> {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CacheSmsValidationRedisRepository cacheSmsValidationRedisRepository;

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String handle(final SignUpCommand event) {
        log.info("execute SignUpCommand = {}", event);

        userRepository.checkIfExistUserByEmail(event.email());

        User user = User.of(
                event.name(), event.email(), bCryptPasswordEncoder.encode(event.password()),
                event.tel(),
                event.role(),
                LocalDateTime.now(), LocalDateTime.now());

        User newUser = userRepository.save(user);

        if (newUser != null) {

            check(!cacheSmsValidationRedisRepository.getIsVerifiedByKey(event.tel()),
                    NOT_VERIFIED_PHONE);

            // 검증 후 해당 버킷 삭제
            cacheSmsValidationRedisRepository.tearDownBucketByKey(
                    new TearDownBucketByKey(event.tel()));

            // 회원가입 후 캐시 버킷 삭제 (이메일 검증 버킷)
            eventPublisher.publishEvent(new TearDownBucketByKey(event.email()));

            // 새로운 유저 회원가입 시 알람 발송
            eventPublisher.publishEvent(new NewUserAlarm(event.name(), event.email(), event.tel()));

            // 새로운 유저 회원가입 시 해당 유저에게 알림톡 발송
            eventPublisher.publishEvent(
                    new SendAlimTalk(null, null, KakaoMsgTemplateType.COMPLETE_SIGNUP, event.tel(),
                            event.name(), null, null, null));

            log.info("SignUpCommand executed successfully");

            return newUser.getName();
        }

        return null;
    }
}
