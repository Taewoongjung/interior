package com.interior.adapter.outbound.jpa.entity.alimtalk;

import com.interior.adapter.outbound.jpa.entity.BaseEntity;
import com.interior.domain.alimtalk.kakaomsgtemplate.AlimTalkButtonLinkType;
import com.interior.domain.alimtalk.kakaomsgtemplate.AlimTalkThirdPartyType;
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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "third_party_type", nullable = false, columnDefinition = "varchar(20)")
    private AlimTalkThirdPartyType thirdPartyType;

    private String messageExtra;

    private String messageSubject;

    private String message;

    private String replaceMessageSubject;

    private String replaceMessage;

    private String buttonInfo;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "button_link_type", nullable = false, columnDefinition = "char(2)")
    private AlimTalkButtonLinkType buttonLinkType;

    private KakaoMsgTemplateEntity(
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
            final AlimTalkButtonLinkType buttonLinkType
    ) {

        super(LocalDateTime.now(), LocalDateTime.now());

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
    }

    // 조회
    public static KakaoMsgTemplateEntity of(
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
            final AlimTalkButtonLinkType buttonLinkType
    ) {

        return new KakaoMsgTemplateEntity(
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
                buttonLinkType
        );
    }

    public KakaoMsgTemplate toPojo() {
        return KakaoMsgTemplate.of(
                getId(),
                getTemplateName(),
                getTemplateCode(),
                getThirdPartyType(),
                getMessageExtra(),
                getMessageSubject(),
                getMessage(),
                getReplaceMessageSubject(),
                getReplaceMessage(),
                getButtonInfo(),
                getButtonLinkType(),
                getCreatedAt(), getLastModified()
        );
    }
}
