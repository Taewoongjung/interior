package com.interior.domain.alimtalk;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class KakaoMsgTemplate {

    private Long id;

    private String templateName;

    private String templateCode;

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
}
