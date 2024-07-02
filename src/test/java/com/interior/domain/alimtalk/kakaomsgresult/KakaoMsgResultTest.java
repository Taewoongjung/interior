package com.interior.domain.alimtalk.kakaomsgresult;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_DELETED;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_KAKAO_MSG_RESULT_MSG_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_KAKAO_MSG_RESULT_RECEIVER_PHONE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_KAKAO_MSG_RESULT_TYPE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_KAKAO_MSG_TEMPLATE_CODE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_KAKAO_MSG_TEMPLATE_NAME;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.domain.alimtalk.AlimTalkMessageType;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("KakaoMsgResult 는 ")
class KakaoMsgResultTest {

    @Test
    @DisplayName("생성된다.")
    void test1() {
        assertDoesNotThrow(() -> KakaoMsgResult.of(
                10L,
                "templateName",
                "templateCode",
                "messageSubject",
                "message",
                AlimTalkMessageType.KKO,
                "receiverPhone",
                "msgId",
                BoolType.F,
                LocalDateTime.now()
        ));
    }

    @Test
    @DisplayName("템플릿 이름이 필수값입니다.")
    void test2() {
        assertThatThrownBy(() -> {
            KakaoMsgResult.of(
                    10L,
                    null,
                    "templateCode",
                    "messageSubject",
                    "message",
                    AlimTalkMessageType.KKO,
                    "receiverPhone",
                    "msgId",
                    BoolType.F,
                    LocalDateTime.now()
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_KAKAO_MSG_TEMPLATE_NAME.getMessage());
    }

    @Test
    @DisplayName("템플릿 코드가 필수값입니다.")
    void test3() {
        assertThatThrownBy(() -> {
            KakaoMsgResult.of(
                    10L,
                    "templateName",
                    null,
                    "messageSubject",
                    "message",
                    AlimTalkMessageType.KKO,
                    "receiverPhone",
                    "msgId",
                    BoolType.F,
                    LocalDateTime.now()
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_KAKAO_MSG_TEMPLATE_CODE.getMessage());
    }

    @Test
    @DisplayName("메시지 결과 타입이 필수값입니다.")
    void test4() {
        assertThatThrownBy(() -> {
            KakaoMsgResult.of(
                    10L,
                    "templateName",
                    "templateCode",
                    "messageSubject",
                    "message",
                    null,
                    "receiverPhone",
                    "msgId",
                    BoolType.F,
                    LocalDateTime.now()
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_KAKAO_MSG_RESULT_TYPE.getMessage());
    }

    @Test
    @DisplayName("메시지를 받은 사람의 번호가 필수값입니다.")
    void test5() {
        assertThatThrownBy(() -> {
            KakaoMsgResult.of(
                    10L,
                    "templateName",
                    "templateCode",
                    "messageSubject",
                    "message",
                    AlimTalkMessageType.KKO,
                    null,
                    "msgId",
                    BoolType.F,
                    LocalDateTime.now()
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_KAKAO_MSG_RESULT_RECEIVER_PHONE.getMessage());
    }

    @Test
    @DisplayName("메시지 결과 id가 필수값입니다.")
    void test6() {
        assertThatThrownBy(() -> {
            KakaoMsgResult.of(
                    10L,
                    "templateName",
                    "templateCode",
                    "messageSubject",
                    "message",
                    AlimTalkMessageType.KKO,
                    "receiverPhone",
                    null,
                    BoolType.F,
                    LocalDateTime.now()
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_KAKAO_MSG_RESULT_MSG_ID.getMessage());
    }

    @Test
    @DisplayName("메시지 결과 성공 유무가 필수값입니다.")
    void test7() {
        assertThatThrownBy(() -> {
            KakaoMsgResult.of(
                    10L,
                    "templateName",
                    "templateCode",
                    "messageSubject",
                    "message",
                    AlimTalkMessageType.KKO,
                    "receiverPhone",
                    "msgId",
                    null,
                    LocalDateTime.now()
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_IS_DELETED.getMessage());
    }
}