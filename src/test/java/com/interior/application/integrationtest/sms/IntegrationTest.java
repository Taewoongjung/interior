package com.interior.application.integrationtest.sms;

import com.interior.adapter.outbound.cache.redis.dto.common.TearDownBucketByKey;
import com.interior.adapter.outbound.cache.redis.email.CacheEmailValidationRedisRepository;
import com.interior.application.command.util.email.EmailService;
import com.interior.domain.user.repository.UserRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Disabled
@SpringBootTest
@ActiveProfiles(value = "dev")
public class IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CacheEmailValidationRedisRepository cacheEmailValidationRedisRepository;

    @Test
    void test1() {
        userRepository.findByEmail("a@a.com");
    }

    @Test
    void test2() throws Exception {
        emailService.sendEmailValidationCheck("aipooh8882@naver.com");
    }

    @Test
    void test3() {

        Map<String, String> data = cacheEmailValidationRedisRepository.getBucketByKey(
                "aipooh8882@naver.com");

        LocalDateTime createdAt = LocalDateTime.parse(data.get("createdAt"));

        Duration duration = Duration.between(createdAt, LocalDateTime.now());
        long diffInMinutes = duration.toMinutes();

        if (diffInMinutes >= 3) {
            cacheEmailValidationRedisRepository.tearDownBucketByKey(
                    new TearDownBucketByKey("aipooh8882@naver.com"));
        }
    }

    @Test
    void test4() {
        cacheEmailValidationRedisRepository.makeBucketByKey("aipooh8882@naver.com", 111);
    }
}