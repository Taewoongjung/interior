package com.interior.adapter.outbound.emitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class EmitterRepository {

    private final Map<String, SseEmitter> emitterList = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    public SseEmitter add(String emitterId, SseEmitter sseEmitter) {
        emitterList.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    public void saveEventCache(String eventCacheId, Object event) {
        eventCache.put(eventCacheId, event);
    }

    public Map<String, SseEmitter> findEmitterByTaskId(final String taskId) {
        return emitterList.entrySet().stream()
                .filter(entry -> taskId.equals(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, SseEmitter> findAllEmitterStartWithByTaskId(final String taskId) {
        return emitterList.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(taskId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Object> findAllEventCacheStartWithByTaskId(final String taskId) {
        return eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(taskId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void deleteById(String id) {
        emitterList.remove(id);
    }

    public void deleteAllEmitterStartWithId(final String taskId) {
        emitterList.forEach(
                (key, emitter) -> {
                    if (key.startsWith(taskId)) {
                        emitterList.remove(key);
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
