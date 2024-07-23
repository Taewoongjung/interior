package com.interior.domain.alimtalk.kakaomsgtemplate;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_KAKAO_MSG_TEMPLATE_CODE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_KAKAO_MSG_TEMPLATE_NAME;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_KAKAO_MSG_TEMPLATE_THIRD_PART_TYPE;
import static com.interior.util.CheckUtil.require;

import com.interior.adapter.inbound.schedule.webdto.AlarmTime;
import com.interior.domain.business.Business;
import com.interior.domain.company.Company;
import com.interior.domain.schedule.BusinessSchedule;
import com.interior.domain.schedule.BusinessScheduleAlarm;
import com.interior.domain.user.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoMsgTemplate {

    private final Long id;

    private final String templateName;

    private final String templateCode;

    private final AlimTalkThirdPartyType thirdPartyType;

    private final String messageExtra;

    private final String messageSubject;

    private String message;

    private final String replaceMessageSubject;

    private final String replaceMessage;

    private final String buttonInfo;

    private final AlimTalkButtonLinkType buttonLinkType;

    private final LocalDateTime createdAt;

    private final LocalDateTime lastModified;

    private KakaoMsgTemplate(
            final Long id,
            final String templateName,
            final String templateCode,
            final AlimTalkThirdPartyType thirdPartyType,
            final String messageExtra,
            final String messageSubject,
            final String message,
            final String replaceMessageSubject,
            final String replaceMessage,
            final String buttonInfo,
            final AlimTalkButtonLinkType buttonLinkType,
            final LocalDateTime createdAt,
            final LocalDateTime lastModified
    ) {

        this.id = id;
        this.templateName = templateName;
        this.templateCode = templateCode;
        this.thirdPartyType = thirdPartyType;
        this.messageExtra = messageExtra;
        this.messageSubject = messageSubject;
        this.message = message;
        this.replaceMessageSubject = replaceMessageSubject;
        this.replaceMessage = replaceMessage;
        this.buttonInfo = buttonInfo;
        this.buttonLinkType = buttonLinkType;
        this.createdAt = createdAt;
        this.lastModified = lastModified;
    }

    public static KakaoMsgTemplate of(
            final Long id,
            final String templateName,
            final String templateCode,
            final AlimTalkThirdPartyType thirdPartyType,
            final String messageExtra,
            final String messageSubject,
            final String message,
            final String replaceMessageSubject,
            final String replaceMessage,
            final String buttonInfo,
            final AlimTalkButtonLinkType buttonLinkType,
            final LocalDateTime createdAt,
            final LocalDateTime lastModified
    ) {

        require(o -> templateName == null, templateName, EMPTY_KAKAO_MSG_TEMPLATE_NAME);
        require(o -> templateCode == null, templateCode, EMPTY_KAKAO_MSG_TEMPLATE_CODE);
        require(o -> thirdPartyType == null, thirdPartyType,
                EMPTY_KAKAO_MSG_TEMPLATE_THIRD_PART_TYPE);

        return new KakaoMsgTemplate(
                id,
                templateName,
                templateCode,
                thirdPartyType,
                messageExtra,
                messageSubject,
                message,
                replaceMessageSubject,
                replaceMessage,
                buttonInfo,
                buttonLinkType,
                createdAt,
                lastModified
        );
    }

    public void replaceArgumentOfTemplate(
            final String customerName,
            final Company company,
            final User user,
            final Business business,
            final BusinessSchedule businessSchedule,
            final BusinessScheduleAlarm businessScheduleAlarm
    ) {

        String finalRes = null;

        if (this.templateCode.equals("TT_6051")) { // [알림톡] 회원가입 완료
            finalRes = this.getMessage().replace("#{고객명}", customerName);
        }

        if (this.templateCode.equals("TT_6052")) { // [알림톡] 견적서 초안 도착
            String replaceStr = this.getMessage().replace("#{요청한사람}", user.getName());

            finalRes = replaceStr.replace("#{회사명}", company.getName());
        }

        if (this.templateCode.equals("TT_9862")) { // [알림톡] 발주 스케줄 알람
            String replaceStr1 = this.getMessage().replace("#{사업명}", business.getName());
            String replaceStr2 = replaceStr1.replace("#{발주명}", businessSchedule.getTitle());
            String replaceStr3 = replaceStr2.replace("#{발주처정보}",
                    businessSchedule.getOrderingPlace());

            String firstMessage = null;

            if (AlarmTime.AT_THE_TIME.equals(businessScheduleAlarm.getSelectedAlarmTime())) {
                firstMessage = "예정 된 발주가 들어오는 시간입니다.";
            } else {
                firstMessage =
                        "예정 된 발주가 들어오기 " + businessScheduleAlarm.getSelectedAlarmTime().getType()
                                + "입니다.";
            }

            finalRes = replaceStr3.replace("#{지정된발주알람시간에맞춘메시지}", firstMessage);
        }

        this.message = finalRes;
    }

    public String getButtonInfo() {
        return buttonInfo.replace("#{url}", "url=http://interiorjung.shop");
    }
}
