package com.interior.application.readmodel.user.handlers;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_VERIFY_NUMBER;
import static com.interior.adapter.common.exception.ErrorType.EXPIRED_PHONE_CHECK_REQUEST;
import static com.interior.adapter.common.exception.ErrorType.INVALID_PHONE_CHECK_NUMBER;
import static com.interior.adapter.common.exception.ErrorType.NOT_6DIGIT_VERIFY_NUMBER;
import static com.interior.util.CheckUtil.check;

import com.interior.abstraction.domain.IQueryHandler;
import com.interior.adapter.outbound.cache.redis.sms.CacheSmsValidationRedisRepository;
import com.interior.application.readmodel.user.queries.ValidationCheckOfPhoneNumberQuery;
import jakarta.validation.ValidationException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@EnableRedisRepositories
@RequiredArgsConstructor
public class ValidationCheckOfPhoneNumberQueryHandler implements
        IQueryHandler<ValidationCheckOfPhoneNumberQuery, Boolean> {

    private final CacheSmsValidationRedisRepository cacheSmsValidationRedisRepository;

    @Override
    public boolean isQueryHandler() {
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean handle(final ValidationCheckOfPhoneNumberQuery query) {
        log.info("ValidationCheckOfPhoneNumberQuery {}", query);

        Map<String, String> data = cacheSmsValidationRedisRepository.getBucketByKey(
                query.targetPhoneNumber());

        LocalDateTime createdAt = LocalDateTime.parse(data.get("createdAt"));
        validateIfOverDurationOfValidation(createdAt);

        String dataCompNumber = data.get("number").trim();

        check("".equals(dataCompNumber), EMPTY_VERIFY_NUMBER); // 빈 값이면 에러
        check(!dataCompNumber.matches("\\d{6}"), NOT_6DIGIT_VERIFY_NUMBER); // 여섯자리 숫자가 아니면 에러

        if (data.get("number").equals(query.compTargetNumber())) {

            cacheSmsValidationRedisRepository.setIsVerifiedByKey(query.targetPhoneNumber());

            return true;

        } else {
            throw new ValidationException(INVALID_PHONE_CHECK_NUMBER.getMessage());
        }
    }

    private void validateIfOverDurationOfValidation(final LocalDateTime target) {

        Duration duration = Duration.between(target, LocalDateTime.now());
        long diffInMinutes = duration.toMinutes();

        check(diffInMinutes > 3, EXPIRED_PHONE_CHECK_REQUEST);
    }
}
