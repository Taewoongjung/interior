package com.interior.application.command.schedule.handlers;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.application.command.schedule.commands.CreateScheduleCommand;
import com.interior.domain.schedule.BusinessSchedule;
import com.interior.domain.schedule.BusinessScheduleAlarm;
import com.interior.domain.schedule.repository.BusinessScheduleRepository;
import com.interior.domain.util.BoolType;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateScheduleCommandHandler implements
        ICommandHandler<CreateScheduleCommand, Boolean> {

    private final BusinessScheduleRepository businessScheduleRepository;

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional
    public Boolean handle(final CreateScheduleCommand event) {

        List<BusinessSchedule> createScheduleList = new ArrayList<>();

        event.relatedBusinessIds().forEach(relatedBusinessId -> {
            createScheduleList.add(BusinessSchedule.of(
                    relatedBusinessId,
                    event.registerId(),
                    event.scheduleType(),
                    event.title(),
                    event.orderingPlace(),
                    event.startDate(),
                    event.endDate(),
                    event.isAlarmOn(),
                    event.colorHexInfo()
            ));
        });

        if (!createScheduleList.isEmpty()) {
            businessScheduleRepository.createSchedule(createScheduleList);
        }

        if (BoolType.T.equals(event.isAlarmOn())) {

            List<BusinessScheduleAlarm> createScheduleAlarmList = new ArrayList<>();

            event.relatedBusinessIds().forEach(relatedBusinessId -> {
                createScheduleAlarmList.add(BusinessScheduleAlarm.of(
                        relatedBusinessId,
                        event.alarmTime(),
                        BoolType.F,
                        BoolType.F,
                        event.selectedAlarmTime()
                ));
            });

            if (!createScheduleAlarmList.isEmpty()) {
                businessScheduleRepository.createAlarmRelatedToSchedule(createScheduleAlarmList);
            }
        }

        return true;
    }
}
