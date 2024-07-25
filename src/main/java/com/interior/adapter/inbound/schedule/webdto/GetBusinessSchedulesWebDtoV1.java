package com.interior.adapter.inbound.schedule.webdto;

import com.interior.domain.schedule.ScheduleType;
import com.interior.domain.util.BoolType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class GetBusinessSchedulesWebDtoV1 {

    public record Req(
            @NotNull
            List<Long> businessIds) {

    }

    public record Res(Long id,
                      Long businessId,
                      Long userId,
                      ScheduleType type,
                      String title,
                      String orderingPlace,
                      LocalDateTime startDate,
                      LocalDateTime endDate,
                      BoolType isAlarmOn,
                      BoolType isDeleted,
                      String colorHexInfo,
                      LocalDateTime createdAt,

                      // 알람
                      LocalDateTime alarmStartDate,
                      AlarmTime selectedAlarmTime) {

    }
}
