package com.interior.domain.alimtalk.kakaomsgtemplate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KakaoMsgTemplateType {

    COMPLETE_SIGNUP("회원가입 완료", "TT_6051"),
    REQUEST_QUOTATION_DRAFT("견적서 초안 도착", "TT_6052"),
    ORDER_SCHEDULE("발주 스케줄 알람", "TT_9862");

    private final String type;
    private final String templateCode;
}
