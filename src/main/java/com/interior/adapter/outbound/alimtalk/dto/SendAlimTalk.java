package com.interior.adapter.outbound.alimtalk.dto;

import com.interior.domain.alimtalk.kakaomsgtemplate.KakaoMsgTemplateType;
import com.interior.domain.business.Business;
import com.interior.domain.company.Company;
import com.interior.domain.user.User;

public record SendAlimTalk(
        KakaoMsgTemplateType template,
        String receiverPhoneNumber,
        String customerName,
        Business business,
        Company company,
        User user
) {

}
