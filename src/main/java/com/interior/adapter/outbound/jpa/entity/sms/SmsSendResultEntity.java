package com.interior.adapter.outbound.jpa.entity.sms;

import com.interior.domain.sms.SmsSendResult;
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
@Table(name = "sms_send_result")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmsSendResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_success", columnDefinition = "char(1)")
    @Enumerated(value = EnumType.STRING)
    private BoolType isSuccess;

    private String type;

    private String sender;

    private String receiver;

    private String platformType;

    private String resultCode;

    private String msgId;

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    private SmsSendResultEntity(
            final Long id,
            final BoolType isSuccess,
            final String type,
            final String sender,
            final String receiver,
            final String platformType,
            final String resultCode,
            final String msgId
    ) {
        this.id = id;
        this.isSuccess = isSuccess;
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.platformType = platformType;
        this.resultCode = resultCode;
        this.msgId = msgId;
        this.createdAt = LocalDateTime.now();
    }

    public static SmsSendResultEntity of(
            final BoolType isSuccess,
            final String type,
            final String sender,
            final String receiver,
            final String platformType,
            final String resultCode,
            final String msgId
    ) {
        return new SmsSendResultEntity(null, isSuccess, type, sender, receiver, platformType,
                resultCode, msgId);
    }

    public SmsSendResult toPojo() {
        return SmsSendResult.of(
                getId(),
                getIsSuccess(),
                getType(),
                getSender(),
                getReceiver(),
                getPlatformType(),
                getResultCode(),
                getMsgId(),
                getCreatedAt()
        );
    }
}
