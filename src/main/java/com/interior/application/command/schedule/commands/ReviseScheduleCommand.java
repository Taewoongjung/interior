package com.interior.application.command.schedule.commands;

import com.interior.abstraction.domain.ICommand;
import com.interior.adapter.inbound.schedule.webdto.AlarmTime;
import com.interior.domain.schedule.ScheduleType;
import com.interior.domain.schedule.repository.dto.ReviseBusinessSchedule;
import com.interior.domain.user.User;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;

public record ReviseScheduleCommand(Long scheduleId,
                                    Long relatedBusinessId,
                                    User revisedUser,
                                    ScheduleType scheduleType,
                                    String title,
                                    String orderingPlace,
                                    LocalDateTime startDate,
                                    LocalDateTime endDate,
                                    BoolType isAlarmOn,
                                    LocalDateTime alarmTime,
                                    String colorHexInfo,
                                    AlarmTime selectedAlarmTime)
        implements ICommand {

    public ReviseBusinessSchedule convertToReviseBusinessSchedule() {
        return new ReviseBusinessSchedule(
                scheduleId,
                relatedBusinessId,
                scheduleType,
                title,
                orderingPlace,
                startDate,
                endDate,
                isAlarmOn,
                alarmTime,
                colorHexInfo
        );
    }
}
