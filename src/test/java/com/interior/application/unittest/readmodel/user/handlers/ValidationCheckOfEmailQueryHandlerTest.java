package com.interior.application.unittest.readmodel.user.handlers;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_VERIFY_NUMBER;
import static com.interior.adapter.common.exception.ErrorType.EXPIRED_EMAIL_CHECK_REQUEST;
import static com.interior.adapter.common.exception.ErrorType.INVALID_EMAIL_CHECK_NUMBER;
import static com.interior.adapter.common.exception.ErrorType.NOT_6DIGIT_VERIFY_NUMBER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.adapter.outbound.cache.redis.email.CacheEmailValidationRedisRepository;
import com.interior.application.readmodel.user.handlers.ValidationCheckOfEmailQueryHandler;
import com.interior.application.readmodel.user.queries.ValidationCheckOfEmailQuery;
import com.interior.helper.config.RedisTestContainerConfig;
import jakarta.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.testcontainers.junit.jupiter.Container;

@DisplayName("ValidationCheckOfEmailQueryHandlerTest 는 ")
class ValidationCheckOfEmailQueryHandlerTest {

    RedisTestContainerConfig redis = new RedisTestContainerConfig();

    @Container
    private final CacheEmailValidationRedisRepository cacheEmailValidationRedisRepository = new CacheEmailValidationRedisRepository(
            redis.getRedisTemplate());

    private final ValidationCheckOfEmailQueryHandler sut = new ValidationCheckOfEmailQueryHandler(
            cacheEmailValidationRedisRepository);

    RedisTemplate<String, Map<String, String>> redisTemplate = redis.getRedisTemplate();

    @BeforeTestClass
    public void setUp() {
        redis.getRedisContainer().start();
    }


    @Test
    @DisplayName("이메일 인증번호를 검증한다.")
    void test1() {

        // given
        String compTargetNumber = "123456";
        String targetEmail = "a@a.com";

        ValidationCheckOfEmailQuery event = new ValidationCheckOfEmailQuery(
                targetEmail, compTargetNumber
        );

        // when
        HashMap<String, String> dateMappedByEmailInCache = new HashMap<>();
        dateMappedByEmailInCache.put("number", compTargetNumber);
        dateMappedByEmailInCache.put("createdAt", LocalDateTime.now().toString());
        dateMappedByEmailInCache.put("isVerified", "false");

        ValueOperations<String, Map<String, String>> cache = redisTemplate.opsForValue();
        cache.set(targetEmail, dateMappedByEmailInCache);

        // then
        assertThat(Objects.requireNonNull(cache.get(targetEmail)).get("isVerified"))
                .isEqualTo("false");

        boolean actual = sut.handle(event);

        assertThat(actual).isTrue();
        assertThat(Objects.requireNonNull(cache.get(targetEmail)).get("isVerified"))
                .isEqualTo("true");
    }

    @Test
    @DisplayName("3분이 지나면 이메일 인증에 실패한다.")
    void test2() {

        // given
        String compTargetNumber = "123456";
        String targetEmail = "a@a.com";

        ValidationCheckOfEmailQuery event = new ValidationCheckOfEmailQuery(
                targetEmail, compTargetNumber
        );

        // when
        HashMap<String, String> dateMappedByEmailInCache = new HashMap<>();
        dateMappedByEmailInCache.put("number", compTargetNumber);
        dateMappedByEmailInCache.put("createdAt",
                String.valueOf(LocalDateTime.now().minusMinutes(4L)));
        dateMappedByEmailInCache.put("isVerified", "false");

        ValueOperations<String, Map<String, String>> cache = redisTemplate.opsForValue();
        cache.set(targetEmail, dateMappedByEmailInCache);

        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EXPIRED_EMAIL_CHECK_REQUEST.getMessage());
    }

    @Test
    @DisplayName("대조 하려는 인증번호가 빈 값이면 이메일 인증에 실패한다.")
    void test3() {

        // given
        String compTargetNumber = "123456";
        String targetEmail = "a@a.com";

        ValidationCheckOfEmailQuery event = new ValidationCheckOfEmailQuery(
                targetEmail, compTargetNumber
        );

        // when
        HashMap<String, String> dateMappedByEmailInCache = new HashMap<>();
        dateMappedByEmailInCache.put("number", "");
        dateMappedByEmailInCache.put("createdAt", LocalDateTime.now().toString());
        dateMappedByEmailInCache.put("isVerified", "false");

        ValueOperations<String, Map<String, String>> cache = redisTemplate.opsForValue();
        cache.set(targetEmail, dateMappedByEmailInCache);

        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_VERIFY_NUMBER.getMessage());
    }

    @Test
    @DisplayName("대조 하려는 인증번호가 6자리가 아니면 이메일 인증에 실패한다.")
    void test4() {

        // given
        String compTargetNumber = "123456";
        String targetEmail = "a@a.com";

        ValidationCheckOfEmailQuery event = new ValidationCheckOfEmailQuery(
                targetEmail, compTargetNumber
        );

        // when
        HashMap<String, String> dateMappedByEmailInCache = new HashMap<>();
        dateMappedByEmailInCache.put("number", "12345");
        dateMappedByEmailInCache.put("createdAt", LocalDateTime.now().toString());
        dateMappedByEmailInCache.put("isVerified", "false");

        ValueOperations<String, Map<String, String>> cache = redisTemplate.opsForValue();
        cache.set(targetEmail, dateMappedByEmailInCache);

        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(NOT_6DIGIT_VERIFY_NUMBER.getMessage());
    }

    @Test
    @DisplayName("대조 하려는 인증번호가 6자리이지만 숫자로만 이루어지지 않으면 이메일 인증에 실패한다.")
    void test5() {

        // given
        String compTargetNumber = "123456";
        String targetEmail = "a@a.com";

        ValidationCheckOfEmailQuery event = new ValidationCheckOfEmailQuery(
                targetEmail, compTargetNumber
        );

        // when
        HashMap<String, String> dateMappedByEmailInCache = new HashMap<>();
        dateMappedByEmailInCache.put("number", "12345a");
        dateMappedByEmailInCache.put("createdAt", LocalDateTime.now().toString());
        dateMappedByEmailInCache.put("isVerified", "false");

        ValueOperations<String, Map<String, String>> cache = redisTemplate.opsForValue();
        cache.set(targetEmail, dateMappedByEmailInCache);

        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(NOT_6DIGIT_VERIFY_NUMBER.getMessage());
    }

    @Test
    @DisplayName("사용자가 입력한 인증번호와 대조 대상 인증번호가 같지 않으면 이메일 인증에 실패한다.")
    void test6() {

        // given
        String compTargetNumber = "111111";
        String targetEmail = "a@a.com";

        ValidationCheckOfEmailQuery event = new ValidationCheckOfEmailQuery(
                targetEmail, compTargetNumber
        );

        // when
        HashMap<String, String> dateMappedByEmailInCache = new HashMap<>();
        dateMappedByEmailInCache.put("number", "123456");
        dateMappedByEmailInCache.put("createdAt", LocalDateTime.now().toString());
        dateMappedByEmailInCache.put("isVerified", "false");

        ValueOperations<String, Map<String, String>> cache = redisTemplate.opsForValue();
        cache.set(targetEmail, dateMappedByEmailInCache);

        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(ValidationException.class)
                .hasMessage(INVALID_EMAIL_CHECK_NUMBER.getMessage());
    }
}