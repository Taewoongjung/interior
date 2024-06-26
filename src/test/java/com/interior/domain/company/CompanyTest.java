package com.interior.domain.company;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.interior.domain.util.BoolType;
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
                "01000",
                3L,
                "메인 주소",
                "서브 주소",
                "빌딩번호",
                "01088257754",
                BoolType.F,
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
                "01000",
                3L,
                "메인 주소",
                "서브 주소",
                "빌딩번호",
                "01088257754",
                BoolType.F,
                LocalDateTime.of(2024, 3, 22, 15, 10),
                LocalDateTime.of(2024, 3, 22, 15, 10)
        ));
    }

    @Test
    @DisplayName("소유자 정보는 필수값이다.")
    void test3() {
        assertThatThrownBy(() -> Company.of(
                1L,
                "홍길동",
                "01000",
                null,
                "메인 주소",
                "서브 주소",
                "빌딩번호",
                "01088257754",
                BoolType.F,
                LocalDateTime.of(2024, 3, 22, 15, 10),
                LocalDateTime.of(2024, 3, 22, 15, 10)
        ));
    }

    @Test
    @DisplayName("전화번호는 필수값이다.")
    void test4() {
        assertThatThrownBy(() -> Company.of(
                1L,
                "홍길동",
                "01000",
                3L,
                "메인 주소",
                "서브 주소",
                "빌딩번호",
                null,
                BoolType.F,
                LocalDateTime.of(2024, 3, 22, 15, 10),
                LocalDateTime.of(2024, 3, 22, 15, 10)
        ));
    }

    @Test
    @DisplayName("우편번호는 필수값이다.")
    void test5() {
        assertThatThrownBy(() -> Company.of(
                1L,
                "홍길동",
                null,
                3L,
                "메인 주소",
                "서브 주소",
                "빌딩번호",
                "01088257754",
                BoolType.F,
                LocalDateTime.of(2024, 3, 22, 15, 10),
                LocalDateTime.of(2024, 3, 22, 15, 10)
        ));
    }

    @Test
    @DisplayName("메인주소는 필수값이다.")
    void test6() {
        assertThatThrownBy(() -> Company.of(
                1L,
                "홍길동",
                "0100",
                3L,
                null,
                "서브 주소",
                "빌딩번호",
                "01088257754",
                BoolType.F,
                LocalDateTime.of(2024, 3, 22, 15, 10),
                LocalDateTime.of(2024, 3, 22, 15, 10)
        ));
    }

    @Test
    @DisplayName("서브주소는 필수값이다.")
    void test7() {
        assertThatThrownBy(() -> Company.of(
                1L,
                "홍길동",
                "0100",
                3L,
                "메인 주소",
                null,
                "빌딩번호",
                "01088257754",
                BoolType.F,
                LocalDateTime.of(2024, 3, 22, 15, 10),
                LocalDateTime.of(2024, 3, 22, 15, 10)
        ));
    }

    @Test
    @DisplayName("빌딩 번호는 필수값이다.")
    void test8() {
        assertThatThrownBy(() -> Company.of(
                1L,
                "홍길동",
                "0100",
                3L,
                "메인 주소",
                "서브 주소",
                null,
                "01088257754",
                BoolType.F,
                LocalDateTime.of(2024, 3, 22, 15, 10),
                LocalDateTime.of(2024, 3, 22, 15, 10)
        ));
    }

    @Test
    @DisplayName("사업체 삭제 여부 값은 필수값이다.")
    void test9() {
        assertThatThrownBy(() -> Company.of(
                1L,
                "홍길동",
                "0100",
                3L,
                "메인 주소",
                "서브 주소",
                "빌딩번호",
                "01088257754",
                null,
                LocalDateTime.of(2024, 3, 22, 15, 10),
                LocalDateTime.of(2024, 3, 22, 15, 10)
        ));
    }
}