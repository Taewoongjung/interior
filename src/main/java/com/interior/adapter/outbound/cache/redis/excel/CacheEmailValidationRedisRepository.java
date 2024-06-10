package com.interior.adapter.outbound.cache.redis.excel;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableRedisRepositories
@RequiredArgsConstructor
public class CacheEmailValidationRedisRepository {

    private final RedisTemplate<String, Map<String, String>> redisTemplate;

    public void makeBucketByKey(final String key, final int number) {
        ValueOperations<String, Map<String, String>> stringStringValueOperations = redisTemplate.opsForValue();

        HashMap<String, String> map = new HashMap<>();
        map.put("number", String.valueOf(number));
        map.put("createdAt", LocalDateTime.now().toString());

        stringStringValueOperations.set(key, map);
    }

    public Map<String, String> getBucketByKey(final String key) {

        ValueOperations<String, Map<String, String>> valueOps = redisTemplate.opsForValue();

        return valueOps.get(key);
    }

    public void tearDownBucketByKey(final String key) {
        redisTemplate.delete(key);
    }
}
