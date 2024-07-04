package com.interior.application.unittest.readmodel.user.handlers;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_VERIFY_NUMBER;
import static com.interior.adapter.common.exception.ErrorType.EXPIRED_PHONE_CHECK_REQUEST;
import static com.interior.adapter.common.exception.ErrorType.INVALID_PHONE_CHECK_NUMBER;
import static com.interior.adapter.common.exception.ErrorType.NOT_6DIGIT_VERIFY_NUMBER;
import static com.interior.helper.config.RedisTestContainerConfig.redisContainer;
import static com.interior.helper.config.RedisTestContainerConfig.redisTemplate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.adapter.outbound.cache.redis.sms.CacheSmsValidationRedisRepository;
import com.interior.application.readmodel.user.handlers.ValidationCheckOfPhoneNumberQueryHandler;
import com.interior.application.readmodel.user.queries.ValidationCheckOfPhoneNumberQuery;
import com.interior.helper.config.RedisTestContainerConfig;
import jakarta.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.testcontainers.junit.jupiter.Container;

@ExtendWith(RedisTestContainerConfig.class)
@DisplayName("ValidationCheckOfPhoneNumberQueryHandler 는 ")
class ValidationCheckOfPhoneNumberQueryHandlerTest {

    @Container
    private final CacheSmsValidationRedisRepository cacheSmsValidationRedisRepository = new CacheSmsValidationRedisRepository(
            redisTemplate);

    private final ValidationCheckOfPhoneNumberQueryHandler sut = new ValidationCheckOfPhoneNumberQueryHandler(
            cacheSmsValidationRedisRepository);

//    @BeforeTestClass
//    public void init() {
//        redisContainer.start();
//    }

    @BeforeTestClass
    public void setUp() {
        redisContainer.start();
        redisTemplate = redisTemplate();
    }

    @Test
    @DisplayName("sms 인증번호를 검증한다.")
    void test1() {

        // given
        String targetPhoneNumber = "01012345678";
        String compTargetNumber = "323232";

        ValidationCheckOfPhoneNumberQuery event = new ValidationCheckOfPhoneNumberQuery(
                targetPhoneNumber, compTargetNumber);

        // when
        HashMap<String, String> dateMappedByPhoneInCache = new HashMap<>();
        dateMappedByPhoneInCache.put("number", compTargetNumber);
        dateMappedByPhoneInCache.put("createdAt", LocalDateTime.now().toString());
        dateMappedByPhoneInCache.put("isVerified", "false");

        ValueOperations<String, Map<String, String>> cache = redisTemplate.opsForValue();
        cache.set(targetPhoneNumber, dateMappedByPhoneInCache);

        // then
        assertThat(Objects.requireNonNull(cache.get(targetPhoneNumber)).get("isVerified"))
                .isEqualTo("false");

        boolean actual = sut.handle(event);

        assertThat(actual).isTrue();
        assertThat(Objects.requireNonNull(cache.get(targetPhoneNumber)).get("isVerified"))
                .isEqualTo("true");
    }

    @Test
    @DisplayName("3분이 지나면 sms 인증에 실패한다.")
    void test2() {

        // given
        String targetPhoneNumber = "01012345678";
        String compTargetNumber = "323232";

        ValidationCheckOfPhoneNumberQuery event = new ValidationCheckOfPhoneNumberQuery(
                targetPhoneNumber, compTargetNumber);

        // when
        HashMap<String, String> dateMappedByPhoneInCache = new HashMap<>();
        dateMappedByPhoneInCache.put("number", compTargetNumber);
        dateMappedByPhoneInCache.put("createdAt",
                String.valueOf(LocalDateTime.now().minusMinutes(4L)));
        dateMappedByPhoneInCache.put("isVerified", "false");

        ValueOperations<String, Map<String, String>> cache = redisTemplate.opsForValue();
        cache.set(targetPhoneNumber, dateMappedByPhoneInCache);

        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EXPIRED_PHONE_CHECK_REQUEST.getMessage());
    }

    @Test
    @DisplayName("대조 하려는 인증번호가 빈 값이면 sms 인증에 실패한다.")
    void test3() {

        // given
        String targetPhoneNumber = "01012345678";
        String compTargetNumber = "323232";

        ValidationCheckOfPhoneNumberQuery event = new ValidationCheckOfPhoneNumberQuery(
                targetPhoneNumber, compTargetNumber);

        // when
        HashMap<String, String> dateMappedByPhoneInCache = new HashMap<>();
        dateMappedByPhoneInCache.put("number", "");
        dateMappedByPhoneInCache.put("createdAt", LocalDateTime.now().toString());
        dateMappedByPhoneInCache.put("isVerified", "false");

        ValueOperations<String, Map<String, String>> cache = redisTemplate.opsForValue();
        cache.set(targetPhoneNumber, dateMappedByPhoneInCache);

        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_VERIFY_NUMBER.getMessage());
    }

    @Test
    @DisplayName("대조 하려는 인증번호가 6자리가 아니면 sms 인증에 실패한다.")
    void test4() {

        // given
        String targetPhoneNumber = "01012345678";
        String compTargetNumber = "323232";

        ValidationCheckOfPhoneNumberQuery event = new ValidationCheckOfPhoneNumberQuery(
                targetPhoneNumber, compTargetNumber);

        // when
        HashMap<String, String> dateMappedByPhoneInCache = new HashMap<>();
        dateMappedByPhoneInCache.put("number", "12345");
        dateMappedByPhoneInCache.put("createdAt", LocalDateTime.now().toString());
        dateMappedByPhoneInCache.put("isVerified", "false");

        ValueOperations<String, Map<String, String>> cache = redisTemplate.opsForValue();
        cache.set(targetPhoneNumber, dateMappedByPhoneInCache);

        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(NOT_6DIGIT_VERIFY_NUMBER.getMessage());
    }

    @Test
    @DisplayName("대조 하려는 인증번호가 6자리이지만 숫자로만 이루어지지 않으면 sms 인증에 실패한다.")
    void test5() {

        // given
        String targetPhoneNumber = "01012345678";
        String compTargetNumber = "323232";

        ValidationCheckOfPhoneNumberQuery event = new ValidationCheckOfPhoneNumberQuery(
                targetPhoneNumber, compTargetNumber);

        // when
        HashMap<String, String> dateMappedByPhoneInCache = new HashMap<>();
        dateMappedByPhoneInCache.put("number", "12345a");
        dateMappedByPhoneInCache.put("createdAt", LocalDateTime.now().toString());
        dateMappedByPhoneInCache.put("isVerified", "false");

        ValueOperations<String, Map<String, String>> cache = redisTemplate.opsForValue();
        cache.set(targetPhoneNumber, dateMappedByPhoneInCache);

        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(NOT_6DIGIT_VERIFY_NUMBER.getMessage());
    }


    @Test
    @DisplayName("사용자가 입력한 인증번호와 대조 대상 인증번호가 같지 않으면 sms 인증에 실패한다.")
    void test6() {

        // given
        String targetPhoneNumber = "01012345678";
        String compTargetNumber = "323232";

        ValidationCheckOfPhoneNumberQuery event = new ValidationCheckOfPhoneNumberQuery(
                targetPhoneNumber, compTargetNumber);

        // when
        HashMap<String, String> dateMappedByPhoneInCache = new HashMap<>();
        dateMappedByPhoneInCache.put("number", "123456");
        dateMappedByPhoneInCache.put("createdAt", LocalDateTime.now().toString());
        dateMappedByPhoneInCache.put("isVerified", "false");

        ValueOperations<String, Map<String, String>> cache = redisTemplate.opsForValue();
        cache.set(targetPhoneNumber, dateMappedByPhoneInCache);

        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(ValidationException.class)
                .hasMessage(INVALID_PHONE_CHECK_NUMBER.getMessage());
    }
}