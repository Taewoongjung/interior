package com.interior.domain.schedule;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_ALARM_START_DATE_IN_SCHEDULE_ALARM;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_SCHEDULE_ID_IN_SCHEDULE_ALARM;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_DELETED_IN_SCHEDULE_ALARM;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_SUCCESS_IN_SCHEDULE_ALARM;
import static com.interior.util.CheckUtil.require;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BusinessScheduleAlarm {

    private final Long id;

    private final Long businessScheduleId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime alarmStartDate;

    private final BoolType isSuccess;

    private final BoolType isDeleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime lastModified;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createdAt;

    private BusinessScheduleAlarm(
            final Long id,
            final Long businessScheduleId,
            final LocalDateTime alarmStartDate,
            final BoolType isSuccess,
            final BoolType isDeleted,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.businessScheduleId = businessScheduleId;
        this.alarmStartDate = alarmStartDate;
        this.isSuccess = isSuccess;
        this.isDeleted = isDeleted;
        this.lastModified = lastModified;
        this.createdAt = createdAt;
    }

    // 조회
    public static BusinessScheduleAlarm of(
            final Long id,
            final Long businessScheduleId,
            final LocalDateTime alarmStartDate,
            final BoolType isSuccess,
            final BoolType isDeleted,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {

        require(o -> businessScheduleId == null, businessScheduleId,
                EMPTY_BUSINESS_SCHEDULE_ID_IN_SCHEDULE_ALARM);
        require(o -> alarmStartDate == null, alarmStartDate,
                EMPTY_ALARM_START_DATE_IN_SCHEDULE_ALARM);
        require(o -> isSuccess == null, isSuccess, EMPTY_IS_SUCCESS_IN_SCHEDULE_ALARM);
        require(o -> isDeleted == null, isDeleted, EMPTY_IS_DELETED_IN_SCHEDULE_ALARM);

        return new BusinessScheduleAlarm(id, businessScheduleId, alarmStartDate, isSuccess,
                isDeleted, lastModified, createdAt);
    }

    // 생성
    public static BusinessScheduleAlarm of(
            final Long businessScheduleId,
            final LocalDateTime alarmStartDate,
            final BoolType isSuccess,
            final BoolType isDeleted
    ) {

        require(o -> businessScheduleId == null, businessScheduleId,
                EMPTY_BUSINESS_SCHEDULE_ID_IN_SCHEDULE_ALARM);
        require(o -> alarmStartDate == null, alarmStartDate,
                EMPTY_ALARM_START_DATE_IN_SCHEDULE_ALARM);
        require(o -> isSuccess == null, isSuccess, EMPTY_IS_SUCCESS_IN_SCHEDULE_ALARM);
        require(o -> isDeleted == null, isDeleted, EMPTY_IS_DELETED_IN_SCHEDULE_ALARM);

        return new BusinessScheduleAlarm(null, businessScheduleId, alarmStartDate, isSuccess,
                isDeleted, null, null);
    }
}
