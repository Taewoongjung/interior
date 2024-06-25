package com.interior.util.converter.jpa.alimtalk.kakao;

import com.interior.adapter.outbound.jpa.entity.alimtalk.KakaoMsgResultEntity;
import com.interior.domain.alimtalk.kakaomsgresult.KakaoMsgResult;

public class KakaoMsgResultEntityConverter {

    public static KakaoMsgResultEntity kakaoMsgResultToEntity(
            final KakaoMsgResult kakaoMsgResult) {

        return KakaoMsgResultEntity.of(
                kakaoMsgResult.getTemplateName(),
                kakaoMsgResult.getTemplateCode(),
                kakaoMsgResult.getMessageSubject(),
                kakaoMsgResult.getMessage(),
                kakaoMsgResult.getMessageType(),
                kakaoMsgResult.getReceiverPhone(),
                kakaoMsgResult.getMsgId(),
                kakaoMsgResult.getIsSuccess()
        );
    }
}
