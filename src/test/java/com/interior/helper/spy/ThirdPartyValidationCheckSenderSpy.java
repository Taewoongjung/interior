package com.interior.helper.spy;

import static com.interior.helper.config.RedisTestContainerConfig.redisTemplate;

import com.interior.abstraction.serviceutill.IThirdPartyValidationCheckSender;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.redis.core.ValueOperations;

public class ThirdPartyValidationCheckSenderSpy implements IThirdPartyValidationCheckSender {

    @Override
    public void sendValidationCheck(String target) {
        int validationNumber = 123456;

        HashMap<String, String> dateMappedByEmailInCache = new HashMap<>();
        dateMappedByEmailInCache.put("number", String.valueOf(validationNumber));
        dateMappedByEmailInCache.put("createdAt", LocalDateTime.now().toString());
        dateMappedByEmailInCache.put("isVerified", "false");

        ValueOperations<String, Map<String, String>> cache = redisTemplate.opsForValue();

        cache.set(target, dateMappedByEmailInCache);
    }
}
