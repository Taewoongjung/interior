package com.interior.application.readmodel.business.handlers;

import com.interior.abstraction.domain.IQueryHandler;
import com.interior.application.readmodel.business.queries.GetExcelProgressInfoQuery;
import com.interior.application.readmodel.utill.sse.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetExcelProgressInfoQueryHandler implements
        IQueryHandler<GetExcelProgressInfoQuery, SseEmitter> {

    private final SseService sseService;

    @Override
    public boolean isQueryHandler() {
        return true;
    }

    @Override
    public SseEmitter handle(final GetExcelProgressInfoQuery query) {
        log.info("process GetExcelProgressInfoQuery {}", query);

        SseEmitter emitter = sseService.addEmitter(query.taskId());

        try {
            sseService.connect(query.taskId());
            sseService.streamData(query.taskId());
        } catch (Exception e) {
            emitter.completeWithError(e);
            throw new RuntimeException("Error during streaming data for task " + query.taskId(), e);
        }

        return emitter;
    }
}
