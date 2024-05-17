package com.interior.domain.business;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

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
                "진행중",
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
                "진행중",
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
                "진행중",
                new ArrayList<>()
        ));
    }

    @Test
    @DisplayName("연관 된 고객 id 는 필수값이다.")
    void test4() {
        assertThatThrownBy(() -> Business.of(
                1L,
                "아파트 사업건",
                10L,
                null,
                "진행중",
                new ArrayList<>()
        ));
    }
}