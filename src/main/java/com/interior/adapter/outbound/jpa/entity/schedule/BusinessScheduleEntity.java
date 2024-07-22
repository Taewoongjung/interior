package com.interior.adapter.outbound.jpa.entity.schedule;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_ID_IN_SCHEDULE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_ALARM_ON_IN_SCHEDULE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_DELETED;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_START_DATE_IN_SCHEDULE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_TYPE_IN_SCHEDULE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_USER_ID_IN_SCHEDULE;
import static com.interior.util.CheckUtil.require;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interior.adapter.outbound.jpa.entity.BaseEntity;
import com.interior.domain.schedule.BusinessSchedule;
import com.interior.domain.schedule.ScheduleType;
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
@Table(name = "business_schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessScheduleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long businessId;

    private Long userId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", columnDefinition = "varchar(20)")
    private ScheduleType type;

    private String title;

    private String orderingPlace;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "is_alarm_on", columnDefinition = "char(1)")
    private BoolType isAlarmOn;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "is_deleted", columnDefinition = "char(1)")
    private BoolType isDeleted;

    private String colorHexInfo;

    private BusinessScheduleEntity(
            final Long id,
            final Long businessId,
            final Long userId,
            final ScheduleType type,
            final String title,
            final String orderingPlace,
            final LocalDateTime startDate,
            final LocalDateTime endDate,
            final BoolType isAlarmOn,
            final BoolType isDeleted,
            final String colorHexInfo
    ) {
        super(LocalDateTime.now(), LocalDateTime.now());

        this.id = id;
        this.businessId = businessId;
        this.userId = userId;
        this.type = type;
        this.title = title;
        this.orderingPlace = orderingPlace;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isAlarmOn = isAlarmOn;
        this.isDeleted = isDeleted;
        this.colorHexInfo = colorHexInfo;
    }

    public static BusinessScheduleEntity of(
            final Long businessId,
            final Long userId,
            final ScheduleType type,
            final String title,
            final String orderingPlace,
            final LocalDateTime startDate,
            final LocalDateTime endDate,
            final BoolType isAlarmOn,
            final BoolType isDeleted,
            final String colorHexInfo
    ) {

        require(o -> businessId == null, businessId, EMPTY_BUSINESS_ID_IN_SCHEDULE);
        require(o -> userId == null, userId, EMPTY_USER_ID_IN_SCHEDULE);
        require(o -> type == null, type, EMPTY_TYPE_IN_SCHEDULE);
        require(o -> startDate == null, startDate, EMPTY_START_DATE_IN_SCHEDULE);
        require(o -> isAlarmOn == null, isAlarmOn, EMPTY_IS_ALARM_ON_IN_SCHEDULE);
        require(o -> isDeleted == null, isDeleted, EMPTY_IS_DELETED);

        return new BusinessScheduleEntity(null, businessId, userId, type, title, orderingPlace,
                startDate, endDate, isAlarmOn, isDeleted, colorHexInfo);
    }

    public BusinessSchedule toPojo() {
        return BusinessSchedule.of(
                getId(),
                getBusinessId(),
                getUserId(),
                getType(),
                getTitle(),
                getOrderingPlace(),
                getStartDate(),
                getEndDate(),
                getIsAlarmOn(),
                getIsDeleted(),
                getColorHexInfo(),
                getLastModified(),
                getCreatedAt()
        );
    }
}
