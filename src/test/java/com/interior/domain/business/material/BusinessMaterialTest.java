package com.interior.domain.business.material;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.interior.domain.business.expense.BusinessMaterialExpense;
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
                LocalDateTime.now(),
                LocalDateTime.now(),
                BusinessMaterialExpense.of(12L, "1000", "500")
        ));
    }

    @Test
    @DisplayName("연관 된 사업 id 는 필수값입니다.")
    void test3() {
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
}