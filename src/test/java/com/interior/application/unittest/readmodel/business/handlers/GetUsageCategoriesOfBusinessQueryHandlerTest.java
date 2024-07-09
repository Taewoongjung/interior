package com.interior.application.unittest.readmodel.business.handlers;

import com.interior.application.readmodel.business.handlers.GetUsageCategoriesOfBusinessQueryHandler;
import com.interior.application.readmodel.business.queryresponses.GetUsageCategoriesOfBusinessQueryResponse;
import com.interior.domain.business.Business;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.util.BoolType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        Long businessId = 22L;

        // when
        when(businessRepository.findById(businessId)).thenReturn(Business.of(
                22L, "사업 현장 2", 43L, 519L, BoolType.F,
                "01000", "부산 해운대구 APEC로 21", "401동 1202호", "2635010500115130000000002",
                LocalDateTime.of(2024, 7, 1, 2, 3),
                LocalDateTime.of(2024, 7, 1, 2, 3),
                getBusinessMaterial(), new ArrayList<>()));

        // then
        List<GetUsageCategoriesOfBusinessQueryResponse> actual = sut.handle(businessId);

        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0).count()).isEqualTo(2);
    }

    private List<BusinessMaterial> getBusinessMaterial() {

        List<BusinessMaterial> list = new ArrayList<>();

        list.add(BusinessMaterial.of(1L, 4L, "벽 타일",
                "외벽 공사", "타일", BigDecimal.valueOf(1), "ea",
                "memo", BoolType.F,
                LocalDateTime.of(2024, 7, 1, 6, 3),
                LocalDateTime.of(2024, 7, 1, 6, 3),
                null
        ));

        list.add(BusinessMaterial.of(2L, 4L, "벽돌",
                "외벽 공사", "돌", BigDecimal.valueOf(1),
                "ea", "memo", BoolType.F,
                LocalDateTime.of(2024, 7, 1, 6, 3),
                LocalDateTime.of(2024, 7, 1, 6, 3),
                null
        ));

        return list;
    }
}