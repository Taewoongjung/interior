package com.interior.adapter.outbound.cache.redis.excel;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.stereotype.Service;

@Service
@EnableRedisRepositories
@RequiredArgsConstructor
public class CacheExcelRedisRepository {

    private final RedisTemplate<String, Map<String, String>> redisTemplate;

    public void makeBucketByKey(final String key, final int totalCount) {
        ValueOperations<String, Map<String, String>> stringStringValueOperations = redisTemplate.opsForValue();

        HashMap<String, String> map = new HashMap<>();
        map.put("completeCount", String.valueOf(0));
        map.put("totalCount", String.valueOf(totalCount));

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
