package com.interior.adapter.outbound.jpa.entity.schedule;

import com.interior.adapter.outbound.jpa.entity.BaseEntity;
import com.interior.domain.schedule.BusinessScheduleAlarm;
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

@Entity
@Getter
@ToString
@Table(name = "business_schedule_alarm")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessScheduleAlarmEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long businessScheduleId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "is_success", columnDefinition = "char(1)")
    private BoolType isSuccess;

    private BusinessScheduleAlarmEntity(
            final Long id,
            final Long businessScheduleId,
            final BoolType isSuccess
    ) {
        super(LocalDateTime.now(), LocalDateTime.now());

        this.id = id;
        this.businessScheduleId = businessScheduleId;
        this.isSuccess = isSuccess;
    }

    public static BusinessScheduleAlarmEntity of(
            final Long businessScheduleId,
            final BoolType isSuccess
    ) {
        return new BusinessScheduleAlarmEntity(null, businessScheduleId, isSuccess);
    }

    public BusinessScheduleAlarm toPojo() {
        return BusinessScheduleAlarm.of(
                getId(),
                getBusinessScheduleId(),
                getIsSuccess(),
                getLastModified(),
                getCreatedAt()
        );
    }
}
