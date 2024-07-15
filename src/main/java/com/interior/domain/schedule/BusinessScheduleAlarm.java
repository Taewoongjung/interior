package com.interior.domain.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BusinessScheduleAlarm {

    private final Long id;

    private final Long businessScheduleId;

    private final BoolType isSuccess;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime lastModified;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createdAt;

    private BusinessScheduleAlarm(
            final Long id,
            final Long businessScheduleId,
            final BoolType isSuccess,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.businessScheduleId = businessScheduleId;
        this.isSuccess = isSuccess;
        this.lastModified = lastModified;
        this.createdAt = createdAt;
    }

    // 조회
    public static BusinessScheduleAlarm of(
            final Long id,
            final Long businessScheduleId,
            final BoolType isSuccess,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {
        return new BusinessScheduleAlarm(id, businessScheduleId, isSuccess, lastModified,
                createdAt);
    }

    // 생성
    public static BusinessScheduleAlarm of(
            final Long businessScheduleId,
            final BoolType isSuccess,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {
        return new BusinessScheduleAlarm(null, businessScheduleId, isSuccess, lastModified,
                createdAt);
    }
}
