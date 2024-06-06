package com.interior.adapter.outbound.emitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class EmitterRepository {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    public void saveEventCache(String eventCacheId, Object event) {
        eventCache.put(eventCacheId, event);
    }

    public Map<String, SseEmitter> findAllEmitterStartWithByTaskId(final String taskId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(taskId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Object> findAllEventCacheStartWithByTaskId(final String taskId) {
        return eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(taskId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void deleteById(String id) {
        emitters.remove(id);
    }

    public void deleteAllEmitterStartWithId(final String taskId) {
        emitters.forEach(
                (key, emitter) -> {
                    if (key.startsWith(taskId)) {
                        emitters.remove(key);
                    }
                }
        );
    }

    public void deleteAllEventCacheStartTaskId(final String taskId) {
        eventCache.forEach(
                (key, emitter) -> {
                    if (key.startsWith(taskId)) {
                        eventCache.remove(key);
                    }
                }
        );
    }
}
