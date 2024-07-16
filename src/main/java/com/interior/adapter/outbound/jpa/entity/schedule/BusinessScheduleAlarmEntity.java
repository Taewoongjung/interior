package com.interior.adapter.outbound.jpa.entity.schedule;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_SCHEDULE_ID_IN_SCHEDULE_ALARM;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_DELETED_IN_SCHEDULE_ALARM;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_SUCCESS_IN_SCHEDULE_ALARM;
import static com.interior.util.CheckUtil.require;

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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "is_deleted", columnDefinition = "char(1)")
    private BoolType isDeleted;

    private BusinessScheduleAlarmEntity(
            final Long id,
            final Long businessScheduleId,
            final BoolType isSuccess,
            final BoolType isDeleted
    ) {
        super(LocalDateTime.now(), LocalDateTime.now());

        this.id = id;
        this.businessScheduleId = businessScheduleId;
        this.isSuccess = isSuccess;
        this.isDeleted = isDeleted;
    }

    public static BusinessScheduleAlarmEntity of(
            final Long businessScheduleId,
            final BoolType isSuccess,
            final BoolType isDeleted
    ) {

        require(o -> businessScheduleId == null, businessScheduleId,
                EMPTY_BUSINESS_SCHEDULE_ID_IN_SCHEDULE_ALARM);
        require(o -> isSuccess == null, isSuccess, EMPTY_IS_SUCCESS_IN_SCHEDULE_ALARM);
        require(o -> isDeleted == null, isDeleted, EMPTY_IS_DELETED_IN_SCHEDULE_ALARM);

        return new BusinessScheduleAlarmEntity(null, businessScheduleId, isSuccess, isDeleted);
    }

    public BusinessScheduleAlarm toPojo() {
        return BusinessScheduleAlarm.of(
                getId(),
                getBusinessScheduleId(),
                getIsSuccess(),
                getIsDeleted(),
                getLastModified(),
                getCreatedAt()
        );
    }
}
