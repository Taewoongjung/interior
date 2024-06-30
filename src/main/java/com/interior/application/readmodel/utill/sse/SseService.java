package com.interior.application.readmodel.utill.sse;

import com.interior.adapter.outbound.cache.redis.excel.CacheExcelRedisRepository;
import com.interior.adapter.outbound.emitter.EmitterRepository;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
@RequiredArgsConstructor
public class SseService {

    private final EmitterRepository emitterRepository;
    private final CacheExcelRedisRepository cacheExcelRedisRepository;

    private static final long DEFAULT_TIMEOUT = 86400000;

    public SseEmitter findById(final String taskId) {
        return emitterRepository.findEmitterByTaskId(taskId).get(taskId);
    }

    public SseEmitter addEmitter(final String taskId) {
        SseEmitter emitter = emitterRepository.add(taskId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(taskId));
        emitter.onTimeout(() -> emitterRepository.deleteById(taskId));
        log.info("emitter 생성 됨 = {}", emitter);
        return emitter;
    }

    public void connect(final String taskId) {
        SseEmitter emitter = findById(taskId);
        log.info("emitter 연결 시도 = {}", emitter);
        log.info("{} 연결 됨", taskId);
        try {
            emitter.send(SseEmitter.event()
                    .name(taskId + " connect")
                    .data("connected"));
        } catch (IOException e) {
            log.error("[Err_msg] Error while connecting emitter for taskId: " + taskId, e);
            throw new RuntimeException(e);
        }
    }

    public void streamData(final String taskId) {
        SseEmitter emitter = findById(taskId);

        try {

            Map<String, String> data = cacheExcelRedisRepository.getBucketByKey(taskId);

            log.info("sse 데이터 보내기 = {}", data);

            if (data != null) {
                emitter.send(SseEmitter.event()
                        .name(taskId)
                        .data(data));
            }
        } catch (IOException e) {
            log.error("[Err_msg] Error while streaming data for taskId: " + taskId, e);
            
            throw new RuntimeException(e);
        }
    }
}
