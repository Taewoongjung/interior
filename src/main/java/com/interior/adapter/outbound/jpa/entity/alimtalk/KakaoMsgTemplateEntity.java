package com.interior.adapter.outbound.jpa.entity.alimtalk;

import com.interior.adapter.outbound.jpa.entity.BaseEntity;
import com.interior.domain.alimtalk.kakaomsgtemplate.AlimTalkButtonLinkType;
import com.interior.domain.alimtalk.kakaomsgtemplate.KakaoMsgTemplate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Table(name = "kakao_msg_template")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoMsgTemplateEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String templateName;

    private String templateCode;

    private String messageExtra;

    private String messageSubject;

    private String message;

    private String replaceMessageSubject;

    private String replaceMessage;

    private String buttonName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "button_link_type", nullable = false, columnDefinition = "char(2)")
    private AlimTalkButtonLinkType buttonLinkType;

    private KakaoMsgTemplateEntity(
            final Long id,
            final String templateName,
            final String templateCode,
            final String messageExtra,
            final String messageSubject,
            final String message,
            final String replaceMessageSubject,
            final String replaceMessage,
            final String buttonName,
            final AlimTalkButtonLinkType buttonLinkType
    ) {

        super(LocalDateTime.now(), LocalDateTime.now());

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
    }

    // 조회
    public static KakaoMsgTemplateEntity of(
            final Long id,
            final String templateName,
            final String templateCode,
            final String messageExtra,
            final String messageSubject,
            final String message,
            final String replaceMessageSubject,
            final String replaceMessage,
            final String buttonName,
            final AlimTalkButtonLinkType buttonLinkType
    ) {

        return new KakaoMsgTemplateEntity(
                id,
                templateName,
                templateCode,
                messageExtra,
                messageSubject,
                message,
                replaceMessageSubject,
                replaceMessage,
                buttonName,
                buttonLinkType
        );
    }

    public KakaoMsgTemplate toPojo() {
        return KakaoMsgTemplate.of(
                getId(),
                getTemplateName(),
                getTemplateCode(),
                getMessageExtra(),
                getMessageSubject(),
                getMessage(),
                getReplaceMessageSubject(),
                getReplaceMessage(),
                getButtonName(),
                getButtonLinkType(),
                getCreatedAt(), getLastModified()
        );
    }
}
