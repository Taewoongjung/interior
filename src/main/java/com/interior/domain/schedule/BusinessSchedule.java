package com.interior.domain.schedule;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_ID_IN_SCHEDULE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_END_DATE_IN_SCHEDULE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_ALARM_ON_IN_SCHEDULE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_DELETED;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_START_DATE_IN_SCHEDULE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_TYPE_IN_SCHEDULE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_USER_ID_IN_SCHEDULE;
import static com.interior.util.CheckUtil.require;

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

        require(o -> businessId == null, businessId, EMPTY_BUSINESS_ID_IN_SCHEDULE);
        require(o -> userId == null, userId, EMPTY_USER_ID_IN_SCHEDULE);
        require(o -> type == null, type, EMPTY_TYPE_IN_SCHEDULE);
        require(o -> startDate == null, startDate, EMPTY_START_DATE_IN_SCHEDULE);
        require(o -> isAlarmOn == null, isAlarmOn, EMPTY_IS_ALARM_ON_IN_SCHEDULE);
        require(o -> isDeleted == null, isDeleted, EMPTY_IS_DELETED);

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

        require(o -> businessId == null, businessId, EMPTY_BUSINESS_ID_IN_SCHEDULE);
        require(o -> userId == null, userId, EMPTY_USER_ID_IN_SCHEDULE);
        require(o -> type == null, type, EMPTY_TYPE_IN_SCHEDULE);
        require(o -> startDate == null, startDate, EMPTY_START_DATE_IN_SCHEDULE);
        require(o -> (ScheduleType.WORK.equals(type) && endDate == null)
                , endDate, EMPTY_END_DATE_IN_SCHEDULE);
        require(o -> isAlarmOn == null, isAlarmOn, EMPTY_IS_ALARM_ON_IN_SCHEDULE);
        require(o -> isDeleted == null, isDeleted, EMPTY_IS_DELETED);

        return new BusinessSchedule(null, businessId, userId, type, title, orderingPlace, startDate,
                endDate, isAlarmOn, isDeleted, lastModified, createdAt);
    }
}
