package com.interior.application.command.user;

import static com.interior.adapter.common.exception.ErrorType.NOT_VERIFIED_EMAIL;
import static com.interior.util.CheckUtil.check;

import com.interior.adapter.inbound.user.webdto.SignUpDto.SignUpReqDto;
import com.interior.adapter.inbound.user.webdto.SignUpDto.SignUpResDto;
import com.interior.adapter.outbound.alarm.dto.event.NewUserAlarm;
import com.interior.adapter.outbound.cache.redis.dto.common.TearDownBucketByKey;
import com.interior.adapter.outbound.cache.redis.email.CacheEmailValidationRedisRepository;
import com.interior.application.command.util.email.EmailService;
import com.interior.application.command.util.sms.SmsUtilService;
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
public class UserCommandService {

    private final EmailService emailService;
    private final SmsUtilService smsUtilService;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CacheEmailValidationRedisRepository cacheEmailValidationRedisRepository;

    @Transactional(rollbackFor = Exception.class)
    public SignUpResDto signUp(final SignUpReqDto req) {

        userRepository.checkIfExistUserByEmail(req.email());

        User user = User.of(
                req.name(), req.email(), bCryptPasswordEncoder.encode(req.password()), req.tel(),
                req.role(),
                LocalDateTime.now(), LocalDateTime.now());

        User newUser = userRepository.save(user);

        if (newUser != null) {

            check(!cacheEmailValidationRedisRepository.getIsVerifiedByKey(req.email()),
                    NOT_VERIFIED_EMAIL);

            // 회원가입 후 캐시 버킷 삭제 (이메일 검증 버킷)
            eventPublisher.publishEvent(new TearDownBucketByKey(req.email()));

            // 새로운 유저 등록 시 알람 발송
            eventPublisher.publishEvent(new NewUserAlarm(req.name(), req.email(), req.tel()));

            return new SignUpResDto(true, newUser.getName());
        }

        return new SignUpResDto(false, null);
    }

    public void sendEmailValidationMail(final String targetEmail) throws Exception {

        // 존재하는 이메일인지 검증
        userRepository.checkIfExistUserByEmail(targetEmail);

        emailService.sendEmailValidationCheck(targetEmail);
    }

    public void sendPhoneValidationSms(final String targetPhoneNumber) throws Exception {

        // 존재하는 휴대폰 번호 인지 검증
        userRepository.checkIfExistUserByPhoneNumber(targetPhoneNumber);

        smsUtilService.sendPhoneValidationSms(targetPhoneNumber);
    }
}
