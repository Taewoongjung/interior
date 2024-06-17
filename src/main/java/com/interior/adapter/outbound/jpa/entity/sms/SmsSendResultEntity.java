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

    @Column(name = "is_success", columnDefinition = "varchar(1)")
    @Enumerated(value = EnumType.STRING)
    private BoolType isSuccess;

    @Column(name = "from", columnDefinition = "varchar(11)")
    private String from;

    @Column(name = "to", columnDefinition = "varchar(11)")
    private String to;

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    private SmsSendResultEntity(
            final Long id,
            final BoolType isSuccess,
            final String from,
            final String to
    ) {
        this.id = id;
        this.isSuccess = isSuccess;
        this.from = from;
        this.to = to;
        this.createdAt = LocalDateTime.now();
    }

    public static SmsSendResultEntity of(
            final BoolType isSuccess,
            final String from,
            final String to
    ) {
        return new SmsSendResultEntity(null, isSuccess, from, to);
    }

    public SmsSendResult toPojo() {
        return SmsSendResult.of(
                getId(),
                getIsSuccess(),
                getFrom(),
                getTo(),
                getCreatedAt()
        );
    }
}
