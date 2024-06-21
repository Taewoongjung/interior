package com.interior.adapter.outbound.jpa.entity.alimtalk;

import com.interior.domain.alimtalk.AlimTalkMessageType;
import com.interior.domain.alimtalk.KakaoMsgResult;
import com.interior.domain.util.BoolType;
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
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@ToString
@Table(name = "kakao_msg_result")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KaKaoMsgResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String templateName;

    private String templateCode;

    private String messageSubject;

    private String message;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "message_type", nullable = false, columnDefinition = "char(3)")
    private AlimTalkMessageType messageType;

    private String receiverPhone;

    private String msgId;

    @Column(name = "is_success", columnDefinition = "char(1)")
    @Enumerated(value = EnumType.STRING)
    private BoolType isSuccess;

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    private KaKaoMsgResultEntity(
            final Long id,
            final String templateName,
            final String templateCode,
            final String messageSubject,
            final String message,
            final AlimTalkMessageType messageType,
            final String receiverPhone,
            final String msgId,
            final BoolType isSuccess
    ) {

        this.id = id;
        this.templateName = templateName;
        this.templateCode = templateCode;
        this.messageSubject = messageSubject;
        this.message = message;
        this.messageType = messageType;
        this.receiverPhone = receiverPhone;
        this.msgId = msgId;
        this.isSuccess = isSuccess;
        this.createdAt = LocalDateTime.now();
    }

    public static KaKaoMsgResultEntity of(
            final String templateName,
            final String templateCode,
            final String messageSubject,
            final String message,
            final AlimTalkMessageType messageType,
            final String receiverPhone,
            final String msgId,
            final BoolType isSuccess
    ) {

        return new KaKaoMsgResultEntity(
                null,
                templateName,
                templateCode,
                messageSubject,
                message,
                messageType,
                receiverPhone,
                msgId,
                isSuccess
        );
    }

    public KakaoMsgResult toPojo() {
        return KakaoMsgResult.of(
                getId(),
                getTemplateName(),
                getTemplateCode(),
                getMessageSubject(),
                getMessage(),
                getMessageType(),
                getReceiverPhone(),
                getMsgId(),
                getIsSuccess(),
                getCreatedAt()
        );
    }
}
