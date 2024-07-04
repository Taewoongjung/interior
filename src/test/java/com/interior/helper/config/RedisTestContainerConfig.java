package com.interior.helper.config;

import java.time.Duration;
import java.util.Map;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public class RedisTestContainerConfig implements BeforeAllCallback {

    private static final String REDIS_IMAGE = "redis:7.0.8-alpine";
    private static final int REDIS_PORT = 6379;
    @Container
    private GenericContainer redis;

    public static RedisTemplate<String, Map<String, String>> redisTemplate;

    @Override
    public void beforeAll(ExtensionContext context) {
        redis = new GenericContainer(DockerImageName.parse(REDIS_IMAGE))
                .withExposedPorts(REDIS_PORT);
        redis.start();
        System.setProperty("spring.data.redis.host", redis.getHost());
        System.setProperty("spring.data.redis.port",
                String.valueOf(redis.getMappedPort(REDIS_PORT)));

        redisTemplate = redisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
    }

    public RedisTemplate<String, Map<String, String>> redisTemplate() {
        RedisTemplate<String, Map<String, String>> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration(
                redis.getHost(), REDIS_PORT);

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
}
