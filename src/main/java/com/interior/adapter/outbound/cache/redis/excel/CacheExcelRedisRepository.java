package com.interior.adapter.outbound.cache.redis.excel;

import com.interior.adapter.outbound.emitter.EmitterRepository;
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

        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < 3) {
            Map<String, String> result = valueOps.get(key);
            if (result != null) {
                return result;
            }
            try {
                Thread.sleep(100); // 100ms 간격으로 조회 시도
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 현재 스레드의 인터럽트 상태를 복원
            }
        }

        // 3초 내에 값을 찾지 못하면 빈 맵 반환
        return new HashMap<>();
    }

    public Map<String, String> getBucketByKey(final String key,
            final EmitterRepository emitterRepository) {

        ValueOperations<String, Map<String, String>> valueOps = redisTemplate.opsForValue();

        Map<String, String> result = valueOps.get(key);
        if (result == null) {
            for (int i = 0; i < 3; i++) {

                result = valueOps.get(key);

                if (result != null) {
                    break;
                }

                try {
                    Thread.sleep(1000);
                } catch (Exception ignored) {

                }
            }
        }

        if (result != null) {

            if (result.get("totalCount").equals(result.get("completeCount"))) {
                emitterRepository.deleteById(key); // emitter 삭제
                tearDownBucketByKey(key); // 버킷 삭제
                log.info("{} 완료 후 삭제", key);
            }

            return result;
        }

        return new HashMap<>();
    }

    public void tearDownBucketByKey(final String key) {
        redisTemplate.delete(key);
    }
}
