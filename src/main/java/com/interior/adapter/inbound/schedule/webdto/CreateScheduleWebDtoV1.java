package com.interior.adapter.inbound.schedule.webdto;

import com.interior.application.command.schedule.commands.CreateScheduleCommand;
import com.interior.domain.schedule.ScheduleType;
import com.interior.domain.user.User;
import com.interior.domain.util.BoolType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class CreateScheduleWebDtoV1 {

    public record Req(

            @NotNull
            ScheduleType scheduleType,

            @NotNull
            Long relatedBusinessId,

            @NotBlank
            String title,

            @Nullable
            String titleWhereStemsFrom,

            @NotNull
            @DateTimeFormat(iso = ISO.DATE_TIME)
            LocalDateTime startDate,

            @Nullable
            @DateTimeFormat(iso = ISO.DATE_TIME)
            LocalDateTime endDate,

            @NotNull
            Boolean isAlarmOn,

            @Nullable
            AlarmTime alarmTime,

            @Nullable
            Boolean isAllDay,
            String colorHexInfo) {

        private LocalDateTime getStartDate() {
            if (isAllDay != null) {
                if (isAllDay) {
                    return LocalDateTime.of(
                            startDate.getYear(),
                            startDate.getMonth(),
                            startDate.getDayOfMonth(),
                            0, 0, 0
                    );
                }
            }

            return startDate;
        }

        private LocalDateTime getEndDate() {

            if (endDate == null) {
                return LocalDateTime.of(
                        startDate.getYear(),
                        startDate.getMonth(),
                        startDate.getDayOfMonth(),
                        23, 59, 59);
            }

            if (isAllDay != null) {
                if (isAllDay) {
                    return LocalDateTime.of(
                            endDate.getYear(),
                            endDate.getMonth(),
                            endDate.getDayOfMonth(),
                            23, 59, 59
                    );
                }
            }

            return endDate;
        }

        public CreateScheduleCommand toCommand(final User registerUser) {

            return new CreateScheduleCommand(
                    relatedBusinessId,
                    registerUser,
                    scheduleType,
                    title,
                    titleWhereStemsFrom,
                    getStartDate(),
                    getEndDate(),
                    isAlarmOn ?
                            BoolType.T : BoolType.F,
                    alarmTime != null ?
                            alarmTime.getAlarmTime(startDate) : null,
                    colorHexInfo,
                    alarmTime
            );
        }
    }
}