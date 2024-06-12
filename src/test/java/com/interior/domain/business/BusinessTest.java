package com.interior.domain.business;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.interior.domain.util.BoolType;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Business 는 ")
class BusinessTest {

    @Test
    @DisplayName("생성된다.")
    void test1() {
        assertDoesNotThrow(() -> Business.of(
                1L,
                "아파트 사업건",
                10L,
                12L,
                BusinessStatus.IN_PROGRESS,
                BusinessStatusDetail.CONSTRUCTING,
                BoolType.F,
                "02830",
                "서울 성북구 아리랑로3",
                "101동 101호",
                "2635010500115140000000001",
                new ArrayList<>()
        ));
    }

    @Test
    @DisplayName("사업명이 필수값이다.")
    void test2() {
        assertThatThrownBy(() -> Business.of(
                1L,
                null,
                10L,
                12L,
                BusinessStatus.IN_PROGRESS,
                null,
                BoolType.F,
                "02830",
                "서울 성북구 아리랑로3",
                "101동 101호",
                "2635010500115140000000001",
                new ArrayList<>()
        ));
    }

    @Test
    @DisplayName("연관 된 회사 id 는 필수값이다.")
    void test3() {
        assertThatThrownBy(() -> Business.of(
                1L,
                "아파트 사업건",
                null,
                12L,
                BusinessStatus.IN_PROGRESS,
                null,
                BoolType.F,
                "02830",
                "서울 성북구 아리랑로3",
                "101동 101호",
                "2635010500115140000000001",
                new ArrayList<>()
        ));
    }
}