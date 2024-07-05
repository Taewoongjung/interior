package com.interior.helper.config;

import com.redis.testcontainers.RedisContainer;
import java.time.Duration;
import java.util.Map;
import org.junit.ClassRule;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class RedisTestContainerConfig implements BeforeAllCallback, AfterAllCallback {

    @ClassRule
    @Container
    public static RedisContainer redisContainer = new RedisContainer(
            RedisContainer.DEFAULT_IMAGE_NAME.withTag(
                    RedisContainer.DEFAULT_TAG))
            .withStartupTimeout(Duration.ofSeconds(30));

    public static RedisTemplate<String, Map<String, String>> redisTemplate;

    @Override
    public void beforeAll(ExtensionContext context) {
        redisContainer.start();
        redisTemplate = redisTemplate();
        redisTemplate.afterPropertiesSet();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
    }

    public static RedisTemplate<String, Map<String, String>> redisTemplate() {
        RedisTemplate<String, Map<String, String>> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    public static LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration(
                redisContainer.getHost(), redisContainer.getRedisPort());

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .shutdownTimeout(Duration.ofMillis(100))
                .commandTimeout(Duration.ofMillis(100))
                .build();

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(
                serverConfig, clientConfig);

        lettuceConnectionFactory.afterPropertiesSet();
        lettuceConnectionFactory.start();

        lettuceConnectionFactory.setShareNativeConnection(false);

        return lettuceConnectionFactory;
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        redisContainer.stop();
    }
}
