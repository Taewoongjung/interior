package com.interior.domain.alimtalk.kakaomsgtemplate;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoMsgTemplate {

    private Long id;

    private String templateName;

    private String templateCode;

    private AlimTalkThirdPartyType thirdPartyType;

    private String messageExtra;

    private String messageSubject;

    private String message;

    private String replaceMessageSubject;

    private String replaceMessage;

    private String buttonInfo;

    private AlimTalkButtonLinkType buttonLinkType;

    private LocalDateTime createdAt;

    private LocalDateTime lastModified;

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

    public void replaceArgumentOfTemplate(final String customerName) {
        String finalRes = null;

        if (this.templateCode.equals("TT_5653")) { // [알림톡] 회원가입 완료
            finalRes = this.getMessage().replace("#{고객명}", customerName);
        }

        this.message = finalRes;
    }
}
