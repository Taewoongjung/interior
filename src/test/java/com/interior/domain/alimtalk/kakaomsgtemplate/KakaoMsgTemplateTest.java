package com.interior.domain.alimtalk.kakaomsgtemplate;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_KAKAO_MSG_TEMPLATE_CODE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_KAKAO_MSG_TEMPLATE_NAME;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_KAKAO_MSG_TEMPLATE_THIRD_PART_TYPE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.interior.adapter.common.exception.InvalidInputException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("KakaoMsgTemplate 는 ")
class KakaoMsgTemplateTest {

    @Test
    @DisplayName("생성된다.")
    void test1() {
        assertDoesNotThrow(() -> KakaoMsgTemplate.of(
                10L,
                "templateName",
                "templateCode",
                AlimTalkThirdPartyType.ALIGO,
                "messageExtra",
                "messageSubject",
                "message",
                "replaceMessageSubject",
                "replaceMessage",
                "buttonInfo",
                AlimTalkButtonLinkType.AL,
                LocalDateTime.now(),
                LocalDateTime.now()
        ));
    }

    @Test
    @DisplayName("템플릿 이름이 필수값입니다.")
    void test2() {
        assertThatThrownBy(() -> {
            KakaoMsgTemplate.of(
                    10L,
                    null,
                    "templateCode",
                    AlimTalkThirdPartyType.ALIGO,
                    "messageExtra",
                    "messageSubject",
                    "message",
                    "replaceMessageSubject",
                    "replaceMessage",
                    "buttonInfo",
                    AlimTalkButtonLinkType.AL,
                    LocalDateTime.now(),
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
            KakaoMsgTemplate.of(
                    10L,
                    "templateName",
                    null,
                    AlimTalkThirdPartyType.ALIGO,
                    "messageExtra",
                    "messageSubject",
                    "message",
                    "replaceMessageSubject",
                    "replaceMessage",
                    "buttonInfo",
                    AlimTalkButtonLinkType.AL,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_KAKAO_MSG_TEMPLATE_CODE.getMessage());
    }

    @Test
    @DisplayName("버튼 타입이 필수값입니다.")
    void test4() {
        assertThatThrownBy(() -> {
            KakaoMsgTemplate.of(
                    10L,
                    "templateName",
                    "templateCode",
                    null,
                    "messageExtra",
                    "messageSubject",
                    "message",
                    "replaceMessageSubject",
                    "replaceMessage",
                    "buttonInfo",
                    AlimTalkButtonLinkType.AL,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_KAKAO_MSG_TEMPLATE_THIRD_PART_TYPE.getMessage());
    }
}