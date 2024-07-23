package com.interior.util.converter.jpa.schedule;

import com.interior.adapter.outbound.jpa.entity.schedule.BusinessScheduleAlarmEntity;
import com.interior.adapter.outbound.jpa.entity.schedule.BusinessScheduleEntity;
import com.interior.domain.schedule.BusinessSchedule;
import com.interior.domain.schedule.BusinessScheduleAlarm;

public class BusinessScheduleEntityConverter {

    public static BusinessScheduleEntity businessScheduleToEntity(
            final BusinessSchedule businessSchedule) {

        return BusinessScheduleEntity.of(
                businessSchedule.getBusinessId(),
                businessSchedule.getUserId(),
                businessSchedule.getType(),
                businessSchedule.getTitle(),
                businessSchedule.getOrderingPlace(),
                businessSchedule.getStartDate(),
                businessSchedule.getEndDate(),
                businessSchedule.getIsAlarmOn(),
                businessSchedule.getIsDeleted(),
                businessSchedule.getColorHexInfo()
        );
    }

    public static BusinessScheduleAlarmEntity businessScheduleAlarmToEntity(
            final BusinessScheduleAlarm businessScheduleAlarm) {

        return BusinessScheduleAlarmEntity.of(
                businessScheduleAlarm.getBusinessScheduleId(),
                businessScheduleAlarm.getAlarmStartDate(),
                businessScheduleAlarm.getIsSuccess(),
                businessScheduleAlarm.getIsDeleted(),
                businessScheduleAlarm.getAlarmTime()
        );
    }
}
