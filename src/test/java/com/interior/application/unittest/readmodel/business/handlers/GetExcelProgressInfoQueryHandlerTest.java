package com.interior.application.unittest.readmodel.business.handlers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.interior.application.readmodel.business.handlers.GetExcelProgressInfoQueryHandler;
import com.interior.application.readmodel.business.queries.GetExcelProgressInfoQuery;
import com.interior.application.readmodel.utill.sse.SseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@DisplayName("GetExcelProgressInfoQueryHandler 는 ")
class GetExcelProgressInfoQueryHandlerTest {

    private final SseService sseService = mock(SseService.class);

    GetExcelProgressInfoQueryHandler sut = new GetExcelProgressInfoQueryHandler(sseService);

    @Test
    @DisplayName("Sse 를 연결 하여 엑셀 진행사항을 업데이트 한다.")
    void test() {

        // given
        GetExcelProgressInfoQuery event = new GetExcelProgressInfoQuery("taskId");

        // when
        SseEmitter sseEmitter = new SseEmitter(86400000L);
        when(sseService.addEmitter(event.taskId())).thenReturn(sseEmitter);

        // then
        SseEmitter actual = sut.handle(event);

        assertThat(actual).isEqualTo(sseEmitter);
    }
}