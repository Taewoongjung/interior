package com.interior.application.command.schedule.handlers;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.adapter.outbound.alimtalk.dto.SendAlimTalk;
import com.interior.application.command.schedule.commands.CreateScheduleCommand;
import com.interior.domain.alimtalk.kakaomsgtemplate.KakaoMsgTemplateType;
import com.interior.domain.business.Business;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.schedule.BusinessSchedule;
import com.interior.domain.schedule.BusinessScheduleAlarm;
import com.interior.domain.schedule.repository.BusinessScheduleRepository;
import com.interior.domain.util.BoolType;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateScheduleCommandHandler implements
        ICommandHandler<CreateScheduleCommand, Boolean> {

    private final BusinessRepository businessRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final BusinessScheduleRepository businessScheduleRepository;

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional
    public Boolean handle(final CreateScheduleCommand command) {
        log.info("execute CreateScheduleCommand = {}", command);

        BusinessSchedule createdSchedule = businessScheduleRepository.createSchedule(
                BusinessSchedule.of(
                        command.relatedBusinessId(),
                        command.registerUser().getId(),
                        command.scheduleType(),
                        command.title(),
                        command.orderingPlace(),
                        command.startDate(),
                        command.endDate(),
                        command.isAlarmOn(),
                        command.colorHexInfo()
                ));

        if (BoolType.T.equals(command.isAlarmOn())) {

            BusinessScheduleAlarm createdBusinessScheduleAlarm = businessScheduleRepository.createAlarmRelatedToSchedule(
                    BusinessScheduleAlarm.of(
                            command.relatedBusinessId(),
                            command.alarmTime(),
                            BoolType.F,
                            BoolType.F,
                            command.selectedAlarmTime()
                    ));

            Duration duration = Duration.between(LocalDateTime.now(),
                    createdBusinessScheduleAlarm.getAlarmStartDate());
            long minutes = duration.toMinutes();

            if (minutes > 10) {
                Business business = businessRepository.findById(command.relatedBusinessId());

                eventPublisher.publishEvent(new SendAlimTalk(
                        createdBusinessScheduleAlarm,
                        createdSchedule,
                        KakaoMsgTemplateType.ORDER_SCHEDULE,
                        command.registerUser().getTel(),
                        command.registerUser().getName(),
                        business,
                        null,
                        null)
                );
            }
        }

        log.info("CreateScheduleCommand executed successfully");
        return true;
    }
}
