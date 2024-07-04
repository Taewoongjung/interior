package com.interior.helper.config;

import com.redis.testcontainers.RedisContainer;
import java.time.Duration;
import java.util.Map;
import lombok.Getter;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Getter
@Testcontainers
public class RedisTestContainerConfig {

    private static final int REDIS_PORT = 6379;

    @Container
    public RedisContainer redisContainer = new RedisContainer(
            RedisContainer.DEFAULT_IMAGE_NAME.withTag(RedisContainer.DEFAULT_TAG));

    public RedisTemplate<String, Map<String, String>> redisTemplate;


    public RedisTestContainerConfig() {
        redisTemplate = redisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.afterPropertiesSet();
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
                redisContainer.getHost(), REDIS_PORT);

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
