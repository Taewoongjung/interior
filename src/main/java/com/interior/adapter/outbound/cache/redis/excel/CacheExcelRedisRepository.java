package com.interior.adapter.outbound.cache.redis.excel;

import com.interior.adapter.outbound.cache.redis.dto.common.TearDownBucketByKey;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
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
        // Redis에서 key에 해당하는 값을 가져옴.
        ValueOperations<String, Map<String, String>> valueOps = redisTemplate.opsForValue();
        Map<String, String> valueMap = valueOps.get(key);

        if (valueMap != null) {
            // "completeCount"의 값을 증가.
            int completeCount = Integer.parseInt(valueMap.get("completeCount")) + 1;
            valueMap.put("completeCount", String.valueOf(completeCount));

            // 변경된 값을 Redis에 다시 저장.
            valueOps.set(key, valueMap);
        } else {
            log.error("[Redis] No value found for key: " + key);
        }
    }

    public Map<String, String> getBucketByKey(final String key) {

        ValueOperations<String, Map<String, String>> valueOps = redisTemplate.opsForValue();

        Map<String, String> result = valueOps.get(key);
        if (result == null) {
            int count = 0;
            do {
                if (count == 100) {
                    break;
                }

                result = valueOps.get(key);

                count++;
            } while (result == null);
        }

        if (result != null) {

            if (result.get("totalCount").equals(result.get("completeCount"))) {
                tearDownBucketByKey(new TearDownBucketByKey(key)); // 버킷 삭제
                log.info("{} 완료 후 삭제", key);
            }
        }

        return result;
    }

    private void tearDownBucketByKey(final TearDownBucketByKey req) {
        redisTemplate.delete(req.key());
    }
}
