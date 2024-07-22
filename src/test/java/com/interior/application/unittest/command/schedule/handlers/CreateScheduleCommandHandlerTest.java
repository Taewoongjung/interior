package com.interior.application.unittest.command.schedule.handlers;

import static com.interior.adapter.common.exception.ErrorType.ALREADY_EXIST_ALARM_INFO_OF_THE_SCHEDULE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.application.command.schedule.commands.CreateScheduleCommand;
import com.interior.application.command.schedule.handlers.CreateScheduleCommandHandler;
import com.interior.domain.schedule.ScheduleType;
import com.interior.domain.schedule.repository.BusinessScheduleRepository;
import com.interior.domain.util.BoolType;
import com.interior.helper.spy.BusinessScheduleRepositorySpy;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateScheduleCommandHandlerTest {

    private final BusinessScheduleRepository businessScheduleRepository = new BusinessScheduleRepositorySpy();

    private final CreateScheduleCommandHandler sut = new CreateScheduleCommandHandler(
            businessScheduleRepository);

    @Test
    @DisplayName("일정 스케줄을 등록할 수 있다.")
    void test1() {

        // given
        List<Long> relatedBusinessIds = new ArrayList<>();
        relatedBusinessIds.add(1L);

        Long registerId = 519L;
        ScheduleType scheduleType = ScheduleType.WORK;
        String title = "~~~ 일정을 등록한다";
        String orderingPlace = null;
        LocalDateTime startDate = LocalDateTime.of(2024, 3, 22, 15, 10);
        LocalDateTime endDate = LocalDateTime.of(2024, 3, 22, 15, 10);
        BoolType isAlarmOn = BoolType.F;
        LocalDateTime alarmTime = null;
        String colorHexInfo = "FF4D4F";

        CreateScheduleCommand event = new CreateScheduleCommand(
                relatedBusinessIds,
                registerId,
                scheduleType,
                title,
                orderingPlace,
                startDate,
                endDate,
                isAlarmOn,
                alarmTime,
                colorHexInfo
        );

        // when
        // then
        sut.handle(event);
    }

    @Test
    @DisplayName("발주 스케줄을 등록할 수 있다.")
    void test2() {

        // given
        List<Long> relatedBusinessIds = new ArrayList<>();
        relatedBusinessIds.add(1L);

        Long registerId = 519L;
        ScheduleType scheduleType = ScheduleType.ORDER;
        String title = "~~~ 발주를 등록한다";
        String orderingPlace = null;
        LocalDateTime startDate = LocalDateTime.of(2024, 3, 22, 15, 10);
        LocalDateTime endDate = LocalDateTime.of(2024, 3, 22, 15, 10);
        BoolType isAlarmOn = BoolType.T;
        LocalDateTime alarmTime = LocalDateTime.of(2024, 3, 22, 15, 10);
        String colorHexInfo = "FF4D4F";

        CreateScheduleCommand event = new CreateScheduleCommand(
                relatedBusinessIds,
                registerId,
                scheduleType,
                title,
                orderingPlace,
                startDate,
                endDate,
                isAlarmOn,
                alarmTime,
                colorHexInfo
        );

        // when
        // then
        sut.handle(event);
    }

    @Test
    @DisplayName("이미 알람 등록이 되어 있으면 발주 스케줄을 등록할 수 없다.")
    void test3() {

        // given
        List<Long> relatedBusinessIds = new ArrayList<>();
        relatedBusinessIds.add(2L);

        Long registerId = 519L;
        ScheduleType scheduleType = ScheduleType.ORDER;
        String title = "~~~ 발주를 등록한다";
        String orderingPlace = null;
        LocalDateTime startDate = LocalDateTime.of(2024, 3, 22, 15, 10);
        LocalDateTime endDate = LocalDateTime.of(2024, 3, 22, 15, 10);
        BoolType isAlarmOn = BoolType.T;
        LocalDateTime alarmTime = LocalDateTime.of(2024, 3, 22, 15, 10);
        String colorHexInfo = "FF4D4F";

        CreateScheduleCommand event = new CreateScheduleCommand(
                relatedBusinessIds,
                registerId,
                scheduleType,
                title,
                orderingPlace,
                startDate,
                endDate,
                isAlarmOn,
                alarmTime,
                colorHexInfo
        );

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(ALREADY_EXIST_ALARM_INFO_OF_THE_SCHEDULE.getMessage());
    }
}