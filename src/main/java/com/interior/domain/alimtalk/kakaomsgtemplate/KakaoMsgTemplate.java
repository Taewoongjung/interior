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

    private String messageExtra;

    private String messageSubject;

    private String message;

    private String replaceMessageSubject;

    private String replaceMessage;

    private String buttonName;

    private AlimTalkButtonLinkType buttonLinkType;

    private LocalDateTime createdAt;

    private LocalDateTime lastModified;

    private KakaoMsgTemplate(
            final Long id,
            final String templateName,
            final String templateCode,
            final String messageExtra,
            final String messageSubject,
            final String message,
            final String replaceMessageSubject,
            final String replaceMessage,
            final String buttonName,
            final AlimTalkButtonLinkType buttonLinkType,
            final LocalDateTime createdAt,
            final LocalDateTime lastModified
    ) {

        this.id = id;
        this.templateName = templateName;
        this.templateCode = templateCode;
        this.messageExtra = messageExtra;
        this.messageSubject = messageSubject;
        this.message = message;
        this.replaceMessageSubject = replaceMessageSubject;
        this.replaceMessage = replaceMessage;
        this.buttonName = buttonName;
        this.buttonLinkType = buttonLinkType;
        this.createdAt = createdAt;
        this.lastModified = lastModified;
    }

    public static KakaoMsgTemplate of(
            final Long id,
            final String templateName,
            final String templateCode,
            final String messageExtra,
            final String messageSubject,
            final String message,
            final String replaceMessageSubject,
            final String replaceMessage,
            final String buttonName,
            final AlimTalkButtonLinkType buttonLinkType,
            final LocalDateTime createdAt,
            final LocalDateTime lastModified
    ) {
        return new KakaoMsgTemplate(
                id,
                templateName,
                templateCode,
                messageExtra,
                messageSubject,
                message,
                replaceMessageSubject,
                replaceMessage,
                buttonName,
                buttonLinkType,
                createdAt,
                lastModified
        );
    }

    public void replaceArgumentOfTemplate(final String customerName) {
        String finalRes = null;

        if (this.templateCode.equals("TT_5460")) { // [알림톡] 회원가입 완료
            String res = this.getMessage().replace("#{고객명}", customerName);
            finalRes = res.replace("#{회사명}", "인테리어정가(鄭家)");
        }

        this.message = finalRes;
    }
}
