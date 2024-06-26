package com.interior.util.converter.jpa.alimtalk.kakao;

import com.interior.adapter.outbound.jpa.entity.alimtalk.KakaoMsgTemplateEntity;
import com.interior.domain.alimtalk.kakaomsgtemplate.KakaoMsgTemplate;

public class KakaoMsgTemplateEntityConverter {

    public static KakaoMsgTemplateEntity KakaoMsgTemplateToEntity(
            final KakaoMsgTemplate kakaoMsgTemplate) {

        return KakaoMsgTemplateEntity.of(
                null,
                kakaoMsgTemplate.getTemplateName(),
                kakaoMsgTemplate.getTemplateCode(),
                kakaoMsgTemplate.getThirdPartyType(),
                kakaoMsgTemplate.getMessageExtra(),
                kakaoMsgTemplate.getMessageSubject(),
                kakaoMsgTemplate.getMessage(),
                kakaoMsgTemplate.getReplaceMessageSubject(),
                kakaoMsgTemplate.getReplaceMessage(),
                kakaoMsgTemplate.getButtonInfo(),
                kakaoMsgTemplate.getButtonLinkType()
        );
    }
}
