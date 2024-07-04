package com.interior.application.unittest.readmodel.business.handlers;

import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.interior.adapter.inbound.business.webdto.GetBusinessWebDtoV1;
import com.interior.application.readmodel.business.handlers.GetBusinessQueryHandler;
import com.interior.application.readmodel.business.queries.GetBusinessQuery;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.helper.spy.BusinessRepositorySpy;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("GetBusinessQueryHandler 는 ")
class GetBusinessQueryHandlerTest {

    private final BusinessRepository businessRepository = new BusinessRepositorySpy();

    private final GetBusinessQueryHandler sut = new GetBusinessQueryHandler(businessRepository);


    @Test
    @DisplayName("해당 사업의 모든 정보가 담긴 GetBusinessWebDtoV1.Response 객체를 return 한다.")
    void test1() {

        // given
        GetBusinessQuery event = new GetBusinessQuery(1L);

        // when
        GetBusinessWebDtoV1.Response actual = sut.handle(event);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.businessName()).isEqualTo("사업 현장 1");
    }

    @Test
    @DisplayName("존재하지 않는 사업은 조회할 수 없다.")
    void test2() {

        // given
        GetBusinessQuery event = new GetBusinessQuery(143L);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_EXIST_BUSINESS.getMessage());
    }
}