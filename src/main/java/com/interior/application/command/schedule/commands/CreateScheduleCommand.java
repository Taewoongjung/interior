package com.interior.application.command.schedule.commands;

import com.interior.abstraction.domain.ICommand;
import com.interior.adapter.inbound.schedule.webdto.AlarmTime;
import com.interior.domain.schedule.ScheduleType;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import java.util.List;

public record CreateScheduleCommand(List<Long> relatedBusinessIds,
                                    Long registerId,
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

}
