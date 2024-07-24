package com.interior.adapter.inbound.schedule.webdto;

import com.interior.application.command.schedule.commands.ReviseScheduleCommand;
import com.interior.domain.schedule.ScheduleType;
import com.interior.domain.user.User;
import com.interior.domain.util.BoolType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ReviseBusinessScheduleWebDtoV1 {

    public record Req(@NotNull
                      Long relatedBusinessId,
                      @NotNull
                      ScheduleType scheduleType,
                      @NotNull
                      String title,
                      String orderingPlace,
                      @NotNull
                      LocalDateTime startDate,
                      @NotNull
                      LocalDateTime endDate,
                      @NotNull
                      Boolean isAlarmOn,
                      LocalDateTime alarmTime,
                      @NotNull
                      String colorHexInfo,
                      AlarmTime selectedAlarmTime) {

        public ReviseScheduleCommand convertToReviseScheduleCommand(
                final Long businessScheduleId,
                final User user
        ) {
            return new ReviseScheduleCommand(
                    businessScheduleId,
                    relatedBusinessId,
                    user,
                    scheduleType,
                    title,
                    orderingPlace,
                    startDate,
                    endDate,
                    isAlarmOn ? BoolType.T : BoolType.F,
                    alarmTime,
                    colorHexInfo,
                    selectedAlarmTime
            );
        }
    }
}
