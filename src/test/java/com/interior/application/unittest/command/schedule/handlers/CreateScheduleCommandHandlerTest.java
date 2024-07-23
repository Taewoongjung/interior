package com.interior.application.unittest.command.schedule.handlers;

import static company.CompanyFixture.COMPANY_LIST;
import static org.mockito.Mockito.mock;

import com.interior.adapter.inbound.schedule.webdto.AlarmTime;
import com.interior.application.command.schedule.commands.CreateScheduleCommand;
import com.interior.application.command.schedule.handlers.CreateScheduleCommandHandler;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.schedule.ScheduleType;
import com.interior.domain.schedule.repository.BusinessScheduleRepository;
import com.interior.domain.user.User;
import com.interior.domain.user.UserRole;
import com.interior.domain.util.BoolType;
import com.interior.helper.spy.BusinessRepositorySpy;
import com.interior.helper.spy.BusinessScheduleRepositorySpy;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

class CreateScheduleCommandHandlerTest {

    private final BusinessRepository businessRepository = new BusinessRepositorySpy();
    private final ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);
    private final BusinessScheduleRepository businessScheduleRepository = new BusinessScheduleRepositorySpy();

    private final CreateScheduleCommandHandler sut = new CreateScheduleCommandHandler(
            businessRepository, eventPublisher, businessScheduleRepository);

    @Test
    @DisplayName("일정 스케줄을 등록할 수 있다.")
    void test1() {

        // given
        Long relatedBusinessId = 1L;

        User registerUser = User.of(
                10L,
                "홍길동",
                "a@a.com",
                "asdqeer1r12jiudjd^312&2ews",
                "01012345678",
                UserRole.ADMIN,
                LocalDateTime.of(2024, 5, 19, 23, 30),
                LocalDateTime.of(2024, 5, 19, 23, 30),
                COMPANY_LIST()
        );
        ScheduleType scheduleType = ScheduleType.WORK;
        String title = "~~~ 일정을 등록한다";
        String orderingPlace = null;
        LocalDateTime startDate = LocalDateTime.of(2024, 3, 22, 15, 10);
        LocalDateTime endDate = LocalDateTime.of(2024, 3, 22, 15, 10);
        BoolType isAlarmOn = BoolType.F;
        AlarmTime selectedAlarmTime = AlarmTime.A_DAY_AGO;
        LocalDateTime alarmTime = null;
        String colorHexInfo = "FF4D4F";

        CreateScheduleCommand event = new CreateScheduleCommand(
                relatedBusinessId,
                registerUser,
                scheduleType,
                title,
                orderingPlace,
                startDate,
                endDate,
                isAlarmOn,
                alarmTime,
                colorHexInfo,
                selectedAlarmTime
        );

        // when
        // then
        sut.handle(event);
    }

    @Test
    @DisplayName("발주 스케줄을 등록할 수 있다.")
    void test2() {

        // given
        Long relatedBusinessId = 1L;

        User registerUser = User.of(
                10L,
                "홍길동",
                "a@a.com",
                "asdqeer1r12jiudjd^312&2ews",
                "01012345678",
                UserRole.ADMIN,
                LocalDateTime.of(2024, 5, 19, 23, 30),
                LocalDateTime.of(2024, 5, 19, 23, 30),
                COMPANY_LIST()
        );
        ScheduleType scheduleType = ScheduleType.ORDER;
        String title = "~~~ 발주를 등록한다";
        String orderingPlace = null;
        LocalDateTime startDate = LocalDateTime.of(2024, 3, 22, 15, 10);
        LocalDateTime endDate = LocalDateTime.of(2024, 3, 22, 15, 10);
        BoolType isAlarmOn = BoolType.T;
        AlarmTime selectedAlarmTime = AlarmTime.A_DAY_AGO;
        LocalDateTime alarmTime = LocalDateTime.of(2024, 3, 22, 15, 10);
        String colorHexInfo = "FF4D4F";

        CreateScheduleCommand event = new CreateScheduleCommand(
                relatedBusinessId,
                registerUser,
                scheduleType,
                title,
                orderingPlace,
                startDate,
                endDate,
                isAlarmOn,
                alarmTime,
                colorHexInfo,
                selectedAlarmTime
        );

        // when
        // then
        sut.handle(event);
    }
}