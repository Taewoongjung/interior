package com.interior.domain.schedule.repository.dto;

import com.interior.domain.schedule.ScheduleType;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;

public record ReviseBusinessSchedule(Long scheduleId,
                                     Long relatedBusinessId,
                                     ScheduleType scheduleType,
                                     String title,
                                     String orderingPlace,
                                     LocalDateTime startDate,
                                     LocalDateTime endDate,
                                     BoolType isAlarmOn,
                                     LocalDateTime alarmTime,
                                     String colorHexInfo) {

}
