package com.interior.adapter.outbound.alimtalk.dto;

import com.interior.domain.alimtalk.kakaomsgtemplate.KakaoMsgTemplateType;
import com.interior.domain.business.Business;
import com.interior.domain.company.Company;
import com.interior.domain.schedule.BusinessSchedule;
import com.interior.domain.schedule.BusinessScheduleAlarm;
import com.interior.domain.user.User;

public record SendAlimTalk(
        BusinessScheduleAlarm businessScheduleAlarm,
        BusinessSchedule businessSchedule,
        KakaoMsgTemplateType template,
        String receiverPhoneNumber,
        String customerName,
        Business business,
        Company company,
        User user
) {

}
