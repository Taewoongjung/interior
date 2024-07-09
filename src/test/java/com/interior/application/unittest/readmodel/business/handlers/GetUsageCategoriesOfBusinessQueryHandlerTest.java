package com.interior.application.unittest.readmodel.business.handlers;

import com.interior.application.readmodel.business.handlers.GetUsageCategoriesOfBusinessQueryHandler;
import com.interior.application.readmodel.business.queryresponses.GetUsageCategoriesOfBusinessQueryResponse;
import com.interior.domain.business.repository.BusinessRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static business.BusinessFixture.B_4;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("GetUsageCategoriesOfBusinessQueryHandler 는 ")
class GetUsageCategoriesOfBusinessQueryHandlerTest {

    private final BusinessRepository businessRepository = mock(BusinessRepository.class);

    private final GetUsageCategoriesOfBusinessQueryHandler sut = new GetUsageCategoriesOfBusinessQueryHandler(businessRepository);


    @Test
    @DisplayName("사업에 해당하는 모든 사업분류를 조회한다.")
    void test1() {

        // given
        Long businessId = 1L;

        // when
        when(businessRepository.findById(businessId)).thenReturn(B_4);

        // then
        List<GetUsageCategoriesOfBusinessQueryResponse> actual = sut.handle(businessId);

        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0).count()).isEqualTo(2);
    }
}