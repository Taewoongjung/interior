package com.interior.domain.sms;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_DELETED;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_PLATFORM_TYPE_SMS_SEND_RESULT;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_RECEIVER_SMS_SEND_RESULT;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_SENDER_SMS_SEND_RESULT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.domain.util.BoolType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("SmsSendResult 는 ")
class SmsSendResultTest {

    @Test
    @DisplayName("생성된다.")
    void test1() {
        assertDoesNotThrow(() -> SmsSendResult.of(
                BoolType.F,
                "type",
                "sender",
                "receiver",
                "platformType",
                "resultCode",
                "msgId"
        ));
    }

    @Test
    @DisplayName("삭제 유무값이 필수값입니다.")
    void test2() {
        assertThatThrownBy(() -> {
            SmsSendResult.of(
                    null,
                    "type",
                    "sender",
                    "receiver",
                    "platformType",
                    "resultCode",
                    "msgId"
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_IS_DELETED.getMessage());
    }

    @Test
    @DisplayName("보낸 사람의 번호가 필수값입니다.")
    void test3() {
        assertThatThrownBy(() -> {
            SmsSendResult.of(
                    BoolType.F,
                    "type",
                    null,
                    "receiver",
                    "platformType",
                    "resultCode",
                    "msgId"
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_SENDER_SMS_SEND_RESULT.getMessage());
    }

    @Test
    @DisplayName("받은 사람의 번호가 필수값입니다.")
    void test4() {
        assertThatThrownBy(() -> {
            SmsSendResult.of(
                    BoolType.F,
                    "type",
                    "sender",
                    null,
                    "platformType",
                    "resultCode",
                    "msgId"
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_RECEIVER_SMS_SEND_RESULT.getMessage());
    }

    @Test
    @DisplayName("발송 플랫폼 정보가 필수값입니다.")
    void test5() {
        assertThatThrownBy(() -> {
            SmsSendResult.of(
                    BoolType.F,
                    "type",
                    "sender",
                    "receiver",
                    null,
                    "resultCode",
                    "msgId"
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_PLATFORM_TYPE_SMS_SEND_RESULT.getMessage());
    }
}