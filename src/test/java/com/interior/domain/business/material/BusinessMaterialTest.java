package com.interior.domain.business.material;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_MATERIAL_AMOUNT;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_MATERIAL_CATEGORY;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_MATERIAL_NAME;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_UNIT;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_RELATED_BUSINESS_TO_BUSINESS_MATERIAL;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_USAGE_CATEGORY_INVALID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.domain.business.expense.BusinessMaterialExpense;
import com.interior.domain.util.BoolType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BusinessMaterial 는 ")
class BusinessMaterialTest {

    @Test
    @DisplayName("생성된다.")
    void test1() {
        assertDoesNotThrow(() -> BusinessMaterial.of(
                10L,
                "크나우프 석고보드",
                "거실 공사",
                "석고판",
                BigDecimal.valueOf(300),
                "ea",
                "석고보드 천정 공사 재료"
        ));
    }

    @Test
    @DisplayName("생성된다. (단가 가격 정보 포함)")
    void test2() {
        assertDoesNotThrow(() -> BusinessMaterial.of(
                12L,
                10L,
                "크나우프 석고보드",
                "거실 공사",
                "석고판",
                BigDecimal.valueOf(300),
                "ea",
                "석고보드 천정 공사 재료",
                BoolType.F,
                LocalDateTime.now(),
                LocalDateTime.now(),
                BusinessMaterialExpense.of(12L, "1000", "500")
        ));
    }

    @Test
    @DisplayName("연관 된 사업 id 는 필수값입니다.")
    void test3() {
        assertThatThrownBy(() -> {
            BusinessMaterial.of(
                    12L,
                    null,
                    "크나우프 석고보드",
                    "거실 공사",
                    "석고판",
                    BigDecimal.valueOf(300),
                    "ea",
                    "석고보드 천정 공사 재료",
                    BoolType.F,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    BusinessMaterialExpense.of(12L, "1000", "500")
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_RELATED_BUSINESS_TO_BUSINESS_MATERIAL.getMessage());
    }

    @Test
    @DisplayName("재료명은 필수값입니다.")
    void test4() {
        assertThatThrownBy(() -> {
            BusinessMaterial.of(
                    12L,
                    10L,
                    null,
                    "거실 공사",
                    "석고판",
                    BigDecimal.valueOf(300),
                    "ea",
                    "석고보드 천정 공사 재료",
                    BoolType.F,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    BusinessMaterialExpense.of(12L, "1000", "500")
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_BUSINESS_MATERIAL_NAME.getMessage());
    }

    @Test
    @DisplayName("사용 분류 카테고리는 필수값입니다.")
    void test5() {
        assertThatThrownBy(() -> {
            BusinessMaterial.of(
                    12L,
                    10L,
                    "크나우프 석고보드",
                    null,
                    "석고판",
                    BigDecimal.valueOf(300),
                    "ea",
                    "석고보드 천정 공사 재료",
                    BoolType.F,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    BusinessMaterialExpense.of(12L, "1000", "500")
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_USAGE_CATEGORY_INVALID.getMessage());
    }

    @Test
    @DisplayName("카테고리는 필수값입니다.")
    void test6() {
        assertThatThrownBy(() -> {
            BusinessMaterial.of(
                    12L,
                    10L,
                    "크나우프 석고보드",
                    "거실 공사",
                    null,
                    BigDecimal.valueOf(300),
                    "ea",
                    "석고보드 천정 공사 재료",
                    BoolType.F,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    BusinessMaterialExpense.of(12L, "1000", "500")
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_BUSINESS_MATERIAL_CATEGORY.getMessage());
    }

    @Test
    @DisplayName("수량은 필수값입니다.")
    void test7() {
        assertThatThrownBy(() -> {
            BusinessMaterial.of(
                    12L,
                    10L,
                    "크나우프 석고보드",
                    "거실 공사",
                    "석고판",
                    null,
                    "ea",
                    "석고보드 천정 공사 재료",
                    BoolType.F,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    BusinessMaterialExpense.of(12L, "1000", "500")
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_BUSINESS_MATERIAL_AMOUNT.getMessage());
    }

    @Test
    @DisplayName("단위는 필수값입니다.")
    void test8() {
        assertThatThrownBy(() -> {
            BusinessMaterial.of(
                    12L,
                    10L,
                    "크나우프 석고보드",
                    "거실 공사",
                    "석고판",
                    BigDecimal.valueOf(300),
                    null,
                    "석고보드 천정 공사 재료",
                    BoolType.F,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    BusinessMaterialExpense.of(12L, "1000", "500")
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_BUSINESS_UNIT.getMessage());
    }
}