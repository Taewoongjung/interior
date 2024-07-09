package com.interior.application.unittest.readmodel.business.handlers;

import com.interior.application.readmodel.business.handlers.GetUsageCategoriesOfBusinessQueryHandler;
import com.interior.application.readmodel.business.queryresponses.GetUsageCategoriesOfBusinessQueryResponse;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.helper.spy.BusinessRepositorySpy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("GetUsageCategoriesOfBusinessQueryHandler 는 ")
class GetUsageCategoriesOfBusinessQueryHandlerTest {

    private final BusinessRepository businessRepository = new BusinessRepositorySpy();

    private final GetUsageCategoriesOfBusinessQueryHandler sut = new GetUsageCategoriesOfBusinessQueryHandler(businessRepository);


    @Test
    @DisplayName("사업에 해당하는 모든 사업분류를 조회한다.")
    void test1() {

        // given
        Long businessId = 1L;

        // when
        // then
        List<GetUsageCategoriesOfBusinessQueryResponse> actual = sut.handle(businessId);

        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0).count()).isEqualTo(1);
    }
}