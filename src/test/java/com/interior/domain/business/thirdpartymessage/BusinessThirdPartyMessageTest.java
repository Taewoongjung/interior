package com.interior.domain.business.thirdpartymessage;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_RESULT_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_SENDER_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.interior.adapter.common.exception.InvalidInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BusinessThirdPartyMessage 는 ")
class BusinessThirdPartyMessageTest {

    @Test
    @DisplayName("생성된다.")
    void test1() {
        assertDoesNotThrow(() -> BusinessThirdPartyMessage.of(
                10L,
                519L,
                1L
        ));
    }

    @Test
    @DisplayName("연관 된 사업 id가 필수값입니다.")
    void test2() {
        assertThatThrownBy(() -> {
            BusinessThirdPartyMessage.of(
                    null,
                    519L,
                    1L
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_BUSINESS_ID.getMessage());
    }

    @Test
    @DisplayName("보낸 사람의 id가 필수값입니다.")
    void test3() {
        assertThatThrownBy(() -> {
            BusinessThirdPartyMessage.of(
                    10L,
                    null,
                    1L
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_SENDER_ID.getMessage());
    }

    @Test
    @DisplayName("보낸 메시지 결과의 id가 필수값입니다.")
    void test4() {
        assertThatThrownBy(() -> {
            BusinessThirdPartyMessage.of(
                    10L,
                    519L,
                    null
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_RESULT_ID.getMessage());
    }
}