package com.interior.application.unittest.readmodel.business.handlers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.interior.application.readmodel.business.handlers.GetBusinessMaterialLogQueryHandler;
import com.interior.application.readmodel.business.queries.GetBusinessMaterialLogQuery;
import com.interior.domain.business.material.log.BusinessMaterialLog;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.helper.spy.BusinessRepositorySpy;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("GetBusinessMaterialLogQueryHandler 는 ")
class GetBusinessMaterialLogQueryHandlerTest {

    private final BusinessRepository businessRepository = new BusinessRepositorySpy();

    private final GetBusinessMaterialLogQueryHandler sut = new GetBusinessMaterialLogQueryHandler(
            businessRepository);

    @Test
    @DisplayName("로그 생성 시간 내림차순으로 재료 수정 로그를 조회한다.")
    void test1() {

        // given
        GetBusinessMaterialLogQuery event = new GetBusinessMaterialLogQuery(10L);

        // when
        List<BusinessMaterialLog> actual = sut.handle(event);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0).getId()).isEqualTo(2L);
        assertThat(actual.get(1).getId()).isEqualTo(1L);
    }
}