package com.interior.adapter.outbound.cache.redis.email;

import com.interior.adapter.outbound.cache.redis.dto.common.TearDownBucketByKey;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Repository
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

    public boolean setIsVerifiedByKey(final String key) {
        ValueOperations<String, Map<String, String>> valueOps = redisTemplate.opsForValue();

        Map<String, String> valueMap = valueOps.get(key);

        if (valueMap != null) {
            valueMap.put("isVerified", "true");
            valueOps.set(key, valueMap);

            return true;
        } else {
            log.error("[Redis] No value found for key: " + key);
        }

        return false;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void tearDownBucketByKey(final TearDownBucketByKey req) {
        redisTemplate.delete(req.key());
    }
}
