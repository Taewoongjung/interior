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

    public void setCountByKey(final String key) {
        // Redis에서 key에 해당하는 값을 가져옵니다.
        ValueOperations<String, Map<String, String>> valueOps = redisTemplate.opsForValue();
        Map<String, String> valueMap = valueOps.get(key);

        if (valueMap != null) {
            // "completeCount"의 값을 증가시킵니다.
            int completeCount = Integer.parseInt(valueMap.get("completeCount")) + 1;
            valueMap.put("completeCount", String.valueOf(completeCount));

            // 변경된 값을 Redis에 다시 저장합니다.
            valueOps.set(key, valueMap);
        } else {
            // valueMap이 null인 경우, 로그를 출력하거나 예외를 던질 수 있습니다.
            System.out.println("No value found for key: " + key);
        }
    }

    public Map<String, String> getBucketByKey(final String key) {
        ValueOperations<String, Map<String, String>> valueOps = redisTemplate.opsForValue();
        return valueOps.get(key);
    }

    public void tearDownBucketByKey(final String key) {
        redisTemplate.delete(key);
    }
}
