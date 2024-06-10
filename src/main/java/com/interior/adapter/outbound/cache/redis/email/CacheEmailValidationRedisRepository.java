package com.interior.adapter.outbound.cache.redis.email;

import com.interior.adapter.outbound.cache.redis.dto.common.TearDownBucketByKey;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        map.put("isVerified", "false");

        stringStringValueOperations.set(key, map);
    }

    public Map<String, String> getBucketByKey(final String key) {

        ValueOperations<String, Map<String, String>> valueOps = redisTemplate.opsForValue();
        return valueOps.get(key);
    }

    public boolean getIsVerifiedByKey(final String key) {
        ValueOperations<String, Map<String, String>> valueOps = redisTemplate.opsForValue();

        Map<String, String> valueMap = valueOps.get(key);

        if (valueMap != null) {
            String value = valueMap.get("isVerified");

            if ("false".equals(value)) {
                return false;
            }
            return true;
        } else {
            log.error("[Redis] No value found for key: " + key);
        }

        return false;
    }

    public void setIsVerifiedByKey(final String key) {
        ValueOperations<String, Map<String, String>> valueOps = redisTemplate.opsForValue();

        Map<String, String> valueMap = valueOps.get(key);

        if (valueMap != null) {
            valueMap.put("isVerified", "true");
            valueOps.set(key, valueMap);
        } else {
            log.error("[Redis] No value found for key: " + key);
        }
    }

    @Async
    @EventListener
    @Transactional
    public void tearDownBucketByKey(final TearDownBucketByKey req) {
        redisTemplate.delete(req.key());
    }
}
