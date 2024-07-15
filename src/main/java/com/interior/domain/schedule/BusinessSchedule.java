package com.interior.domain.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BusinessSchedule {

    private final Long id;

    private final Long businessId;

    private final Long userId;

    private final ScheduleType type;

    private final String title;

    private final String orderingPlace;

    private final LocalDateTime startDate;

    private final LocalDateTime endDate;

    private final BoolType isAlarmOn;

    private final BoolType isDeleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime lastModified;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createdAt;


    private BusinessSchedule(
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
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {
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
        this.lastModified = lastModified;
        this.createdAt = createdAt;
    }

    // 조회
    public static BusinessSchedule of(
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
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {
        return new BusinessSchedule(id, businessId, userId, type, title, orderingPlace, startDate,
                endDate, isAlarmOn, isDeleted, lastModified, createdAt);
    }

    // 생성
    public static BusinessSchedule of(
            final Long businessId,
            final Long userId,
            final ScheduleType type,
            final String title,
            final String orderingPlace,
            final LocalDateTime startDate,
            final LocalDateTime endDate,
            final BoolType isAlarmOn,
            final BoolType isDeleted,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {
        return new BusinessSchedule(null, businessId, userId, type, title, orderingPlace, startDate,
                endDate, isAlarmOn, isDeleted, lastModified, createdAt);
    }
}
