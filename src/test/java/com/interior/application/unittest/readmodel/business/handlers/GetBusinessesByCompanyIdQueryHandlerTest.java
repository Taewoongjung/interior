package com.interior.application.unittest.readmodel.business.handlers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.interior.adapter.inbound.business.enumtypes.QueryType;
import com.interior.application.readmodel.business.handlers.GetBusinessesByCompanyIdQueryHandler;
import com.interior.application.readmodel.business.queries.GetBusinessesByCompanyIdQuery;
import com.interior.domain.business.Business;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.helper.spy.BusinessRepositorySpy;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("GetBusinessesByCompanyIdQueryHandler 는 ")
class GetBusinessesByCompanyIdQueryHandlerTest {

    private final BusinessRepository businessRepository = new BusinessRepositorySpy();

    private final GetBusinessesByCompanyIdQueryHandler sut = new GetBusinessesByCompanyIdQueryHandler(
            businessRepository);


    @Test
    @DisplayName("queryType 이 \"business_management(사업관리)\"일 때 연관 객체와 함께 Business list 를 반환한다.")
    void test1() {

        // given
        GetBusinessesByCompanyIdQuery event = new GetBusinessesByCompanyIdQuery(23L,
                QueryType.business_management);

        // when
        List<Business> actual = sut.handle(event);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0).getId()).isEqualTo(2L);
        assertThat(actual.get(0).getBusinessMaterialList()).isNotNull();
        assertThat(actual.get(0).getBusinessProgressList()).isNotNull();
        assertThat(actual.get(1).getId()).isEqualTo(21L);
        assertThat(actual.get(1).getBusinessProgressList()).isNotNull();
        assertThat(actual.get(1).getBusinessProgressList()).isNotNull();
    }

    @Test
    @DisplayName("queryType 이 \"none(입력 안됨)\"일 때 연관 객체 제외 후 Business list 를 반환한다.")
    void test2() {

        // given
        GetBusinessesByCompanyIdQuery event = new GetBusinessesByCompanyIdQuery(23L,
                QueryType.none);

        // when
        List<Business> actual = sut.handle(event);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0).getId()).isEqualTo(2L);
        assertThat(actual.get(0).getBusinessMaterialList()).isNull();
        assertThat(actual.get(0).getBusinessProgressList()).isNull();
        assertThat(actual.get(1).getId()).isEqualTo(21L);
        assertThat(actual.get(1).getBusinessProgressList()).isNull();
        assertThat(actual.get(1).getBusinessProgressList()).isNull();
    }
}