package com.interior.domain.business.expense;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_MATERIAL_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.interior.adapter.common.exception.InvalidInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BusinessMaterialExpense 는 ")
class BusinessMaterialExpenseTest {

    @Test
    @DisplayName("생성된다.")
    void test1() {
        assertDoesNotThrow(() -> BusinessMaterialExpense.of(
                10L,
                "15000",
                "2300"
        ));
    }

    @Test
    @DisplayName("연관 된 사업 재료의 id가 필수값입니다.")
    void test2() {
        assertThatThrownBy(() -> {
            BusinessMaterialExpense.of(
                    null,
                    "15000",
                    "2300"
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_BUSINESS_MATERIAL_ID.getMessage());
    }
}