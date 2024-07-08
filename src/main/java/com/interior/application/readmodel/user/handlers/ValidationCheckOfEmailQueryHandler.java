package com.interior.application.readmodel.user.handlers;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_VERIFY_NUMBER;
import static com.interior.adapter.common.exception.ErrorType.EXPIRED_EMAIL_CHECK_REQUEST;
import static com.interior.adapter.common.exception.ErrorType.INVALID_EMAIL_CHECK_NUMBER;
import static com.interior.adapter.common.exception.ErrorType.NOT_6DIGIT_VERIFY_NUMBER;
import static com.interior.util.CheckUtil.check;

import com.interior.abstraction.domain.IQueryHandler;
import com.interior.adapter.outbound.cache.redis.email.CacheEmailValidationRedisRepository;
import com.interior.application.readmodel.user.queries.ValidationCheckOfEmailQuery;
import jakarta.validation.ValidationException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationCheckOfEmailQueryHandler implements
        IQueryHandler<ValidationCheckOfEmailQuery, Boolean> {

    private final CacheEmailValidationRedisRepository cacheEmailValidationRedisRepository;

    @Override
    public boolean isQueryHandler() {
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean handle(final ValidationCheckOfEmailQuery query) {
        log.info("ValidationCheckOfEmailQuery {}", query);

        Map<String, String> data = cacheEmailValidationRedisRepository.getBucketByKey(
                query.targetEmail());

        LocalDateTime createdAt = LocalDateTime.parse(data.get("createdAt"));
        validateIfOverDurationOfValidation(createdAt);

        String dataCompNumber = data.get("number").trim();

        check("".equals(dataCompNumber), EMPTY_VERIFY_NUMBER); // 빈 값이면 에러
        check(!dataCompNumber.matches("\\d{6}"), NOT_6DIGIT_VERIFY_NUMBER); // 여섯자리 숫자가 아니면 에러

        if (data.get("number").equals(query.compTargetNumber())) {

            // 인증번호가 같으면 isVerified = true 로 수정
            boolean res = cacheEmailValidationRedisRepository.setIsVerifiedByKey(
                    query.targetEmail());

            if (!res) {
                throw new ValidationException(INVALID_EMAIL_CHECK_NUMBER.getMessage());
            }

            return true;

        } else {
            throw new ValidationException(INVALID_EMAIL_CHECK_NUMBER.getMessage());
        }
    }

    private void validateIfOverDurationOfValidation(final LocalDateTime target) {

        Duration duration = Duration.between(target, LocalDateTime.now());
        long diffInMinutes = duration.toMinutes();

        check(diffInMinutes > 3, EXPIRED_EMAIL_CHECK_REQUEST);
    }
}
