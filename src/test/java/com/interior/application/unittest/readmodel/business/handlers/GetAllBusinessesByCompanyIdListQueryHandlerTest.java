package com.interior.application.unittest.readmodel.business.handlers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.interior.application.readmodel.business.handlers.GetAllBusinessesByCompanyIdListQueryHandler;
import com.interior.application.readmodel.business.queries.GetAllBusinessesByCompanyIdListQuery;
import com.interior.domain.business.Business;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.helper.spy.BusinessRepositorySpy;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("GetAllBusinessesByCompanyIdListQueryHandler 는 ")
class GetAllBusinessesByCompanyIdListQueryHandlerTest {

    private final BusinessRepository businessRepository = new BusinessRepositorySpy();

    private final GetAllBusinessesByCompanyIdListQueryHandler sut = new GetAllBusinessesByCompanyIdListQueryHandler(
            businessRepository);


    @Test
    @DisplayName("Business list 를 반환한다.")
    void test1() {

        // given
        List<Long> companyIdList = new ArrayList<>();
        companyIdList.add(17L);
        companyIdList.add(23L);

        GetAllBusinessesByCompanyIdListQuery event = new GetAllBusinessesByCompanyIdListQuery(
                companyIdList);

        // when
        List<Business> actual = sut.handle(event);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(3);

        assertThat(actual.get(0).getCompanyId()).isEqualTo(17L);
        assertThat(actual.get(1).getCompanyId()).isEqualTo(23L);
    }

    @Test
    @DisplayName("삭제 된 Business를 제외 한 list 를 반환한다.")
    void test2() {

        // given
        List<Long> companyIdList = new ArrayList<>();
        companyIdList.add(17L);
        companyIdList.add(56L);

        GetAllBusinessesByCompanyIdListQuery event = new GetAllBusinessesByCompanyIdListQuery(
                companyIdList);

        // when
        List<Business> actual = sut.handle(event);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(1);

        assertThat(actual.get(0).getCompanyId()).isEqualTo(17L);
    }
}