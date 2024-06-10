package com.interior.application.query.user;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_VERIFY_NUMBER;
import static com.interior.adapter.common.exception.ErrorType.EXPIRED_ACCESS_TOKEN;
import static com.interior.adapter.common.exception.ErrorType.EXPIRED_EMAIL_CHECK_REQUEST;
import static com.interior.adapter.common.exception.ErrorType.INVALID_EMAIL_CHECK_NUMBER;
import static com.interior.adapter.common.exception.ErrorType.NOT_6DIGIT_VERIFY_NUMBER;
import static com.interior.util.CheckUtil.check;

import com.interior.adapter.config.security.jwt.JWTUtil;
import com.interior.adapter.outbound.cache.redis.email.CacheEmailValidationRedisRepository;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import jakarta.validation.ValidationException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserQueryService implements UserDetailsService {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final CacheEmailValidationRedisRepository cacheEmailValidationRedisRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        log.info("'{}' 는 찾을 수 없는 이메일입니다.", email);

        if (user != null) {
            return user;
        }

        return null;
    }

    @Transactional(readOnly = true)
    public User loadUserByToken(final String reqToken) throws UsernameNotFoundException {

        String token = jwtUtil.getTokenWithoutBearer(reqToken);

        check(jwtUtil.isExpired(token), EXPIRED_ACCESS_TOKEN);

        User user = userRepository.findByEmail(jwtUtil.getEmail(token));

        if (user != null) {
            return user;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public boolean validationCheckOfEmail(final String targetEmail, final String compTargetNumber)
            throws Exception {

        Map<String, String> data = cacheEmailValidationRedisRepository.getBucketByKey(targetEmail);

        LocalDateTime createdAt = LocalDateTime.parse(data.get("createdAt"));
        Duration duration = Duration.between(createdAt, LocalDateTime.now());
        long diffInMinutes = duration.toMinutes();

        if (diffInMinutes > 3) { // 3분이 지나면 버킷 삭제 (만료 처리)
            throw new Exception(EXPIRED_EMAIL_CHECK_REQUEST.getMessage());
        }

        String dataCompNumber = data.get("number").trim();

        check("".equals(dataCompNumber), EMPTY_VERIFY_NUMBER); // 빈 값이면 에러
        check(!dataCompNumber.matches("\\d{6}"), NOT_6DIGIT_VERIFY_NUMBER); // 여섯자리 숫자가 아니면 에러

        if (data.get("number").equals(compTargetNumber)) {

            // 인증번호가 같으면 isVerified = true 로 수정
            cacheEmailValidationRedisRepository.setIsVerifiedByKey(targetEmail);

            return true;

        } else {
            throw new ValidationException(INVALID_EMAIL_CHECK_NUMBER.getMessage());
        }
    }
}
