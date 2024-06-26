package com.interior.domain.alimtalk.kakaomsgtemplate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KakaoMsgTemplateType {

    COMPLETE_SIGNUP("회원가입 완료");

    private final String type;
}
