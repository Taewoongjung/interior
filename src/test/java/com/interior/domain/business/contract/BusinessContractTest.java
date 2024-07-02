package com.interior.domain.business.contract;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_COMPANY_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_CONTRACT_TYPE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_CONTRACT_USER_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_AGREED;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_DELETED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.domain.util.BoolType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BusinessContract 는 ")
class BusinessContractTest {

    @Test
    @DisplayName("생성된다.")
    void test1() {
        assertDoesNotThrow(() -> BusinessContract.of(
                10L,
                31L,
                ContractType.SIGNED,
                BoolType.T,
                519L,
                BoolType.F
        ));
    }

    @Test
    @DisplayName("연관 된 사업체 id가 필수값입니다.")
    void test2() {
        assertThatThrownBy(() -> {
            BusinessContract.of(
                    null,
                    31L,
                    ContractType.SIGNED,
                    BoolType.T,
                    519L,
                    BoolType.F
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_COMPANY_ID.getMessage());
    }

    @Test
    @DisplayName("연관 된 사업 id가 필수값입니다.")
    void test3() {
        assertThatThrownBy(() -> {
            BusinessContract.of(
                    10L,
                    null,
                    ContractType.SIGNED,
                    BoolType.T,
                    519L,
                    BoolType.F
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_BUSINESS_ID.getMessage());
    }

    @Test
    @DisplayName("계약 타입이 필수값입니다.")
    void test4() {
        assertThatThrownBy(() -> {
            BusinessContract.of(
                    10L,
                    31L,
                    null,
                    BoolType.T,
                    519L,
                    BoolType.F
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_CONTRACT_TYPE.getMessage());
    }

    @Test
    @DisplayName("고객의 동의 객체가 필수값입니다.")
    void test5() {
        assertThatThrownBy(() -> {
            BusinessContract.of(
                    10L,
                    31L,
                    ContractType.SIGNED,
                    null,
                    519L,
                    BoolType.F
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_IS_AGREED.getMessage());
    }

    @Test
    @DisplayName("동의 한 고객의 id가 필수값입니다.")
    void test6() {
        assertThatThrownBy(() -> {
            BusinessContract.of(
                    10L,
                    31L,
                    ContractType.SIGNED,
                    BoolType.T,
                    null,
                    BoolType.F
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_CONTRACT_USER_ID.getMessage());
    }

    @Test
    @DisplayName("삭제 유무 객체가 필수값입니다.")
    void test7() {
        assertThatThrownBy(() -> {
            BusinessContract.of(
                    10L,
                    31L,
                    ContractType.SIGNED,
                    BoolType.T,
                    519L,
                    null
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_IS_DELETED.getMessage());
    }
}