package com.interior.domain.company;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Company 는 ")
class CompanyTest {

    @Test
    @DisplayName("생성된다.")
    void test1() {
        assertDoesNotThrow(() -> Company.of(
                1L,
                "홍길동",
                "메인 주소",
                "서브 주소",
                "빌딩번호",
                "01088257754",
                LocalDateTime.of(2024, 3, 22, 15, 10),
                LocalDateTime.of(2024, 3, 22, 15, 10)
        ));
    }

    @Test
    @DisplayName("이름이 필수값이다.")
    void test2() {
        assertThatThrownBy(() -> Company.of(
                1L,
                null,
                "메인 주소",
                "서브 주소",
                "빌딩번호",
                "01088257754",
                LocalDateTime.of(2024, 3, 22, 15, 10),
                LocalDateTime.of(2024, 3, 22, 15, 10)
        ));
    }

    @Test
    @DisplayName("이름이 필수값이다.")
    void test3() {
        assertThatThrownBy(() -> Company.of(
                1L,
                "홍길동",
                "메인 주소",
                "서브 주소",
                "빌딩번호",
                null,
                LocalDateTime.of(2024, 3, 22, 15, 10),
                LocalDateTime.of(2024, 3, 22, 15, 10)
        ));
    }
}