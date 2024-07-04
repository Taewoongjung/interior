package com.interior.application.unittest.readmodel.business.handlers;

import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.interior.adapter.outbound.cache.redis.excel.CacheExcelRedisRepository;
import com.interior.application.readmodel.business.handlers.GetExcelOfBusinessMaterialListQueryHandler;
import com.interior.application.readmodel.business.queries.GetExcelOfBusinessMaterialListQuery;
import com.interior.application.readmodel.utill.sse.SseService;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.helper.spy.BusinessRepositorySpy;
import jakarta.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;

@DisplayName("GetExcelOfBusinessMaterialListQueryHandler 는 ")
class GetExcelOfBusinessMaterialListQueryHandlerTest {

    private final SseService sseService = mock(SseService.class);
    private final BusinessRepository businessRepository = new BusinessRepositorySpy();
    private final CacheExcelRedisRepository cacheExcelRedisRepository = mock(
            CacheExcelRedisRepository.class);

    private final GetExcelOfBusinessMaterialListQueryHandler sut = new GetExcelOfBusinessMaterialListQueryHandler(
            sseService, businessRepository, cacheExcelRedisRepository);


    @Test
    @DisplayName("엑셀을 다운로드 할 수 있다.")
    void test1() {

        // given
        HttpServletResponse response = new MockHttpServletResponse();
        GetExcelOfBusinessMaterialListQuery event = new GetExcelOfBusinessMaterialListQuery(
                17L, 1L, "taskId-213124512", response
        );

        // when
        // then
        sut.handle(event);
    }

    @Test
    @DisplayName("존재하지 않는 사업체의 엑셀은 다운로드 할 수 없다.")
    void test2() {

        // given
        HttpServletResponse response = new MockHttpServletResponse();
        GetExcelOfBusinessMaterialListQuery event = new GetExcelOfBusinessMaterialListQuery(
                12L, 1L, "taskId-213124512", response
        );

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_EXIST_BUSINESS.getMessage());
    }

    @Test
    @DisplayName("존재하지 않는 사업의 엑셀은 다운로드 할 수 없다.")
    void test3() {

        // given
        HttpServletResponse response = new MockHttpServletResponse();
        GetExcelOfBusinessMaterialListQuery event = new GetExcelOfBusinessMaterialListQuery(
                17L, 2L, "taskId-213124512", response
        );

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_EXIST_BUSINESS.getMessage());
    }
}