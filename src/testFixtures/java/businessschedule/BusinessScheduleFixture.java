package businessschedule;

import com.interior.domain.schedule.BusinessSchedule;
import com.interior.domain.schedule.ScheduleType;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;

public class BusinessScheduleFixture {

    public static BusinessSchedule BS_1 = BusinessSchedule.of(
            100L,
            1L,
            519L,
            ScheduleType.WORK,
            "일정 스케줄입니다",
            null,
            LocalDateTime.of(2024, 7, 1, 2, 3),
            LocalDateTime.of(2024, 7, 2, 2, 3),
            BoolType.F,
            BoolType.F,
            "colorHexInfo",
            LocalDateTime.of(2024, 7, 1, 2, 3),
            LocalDateTime.of(2024, 7, 1, 2, 3)
    );

    public static BusinessSchedule BS_2 = BusinessSchedule.of(
            101L,
            1L,
            519L,
            ScheduleType.ORDER,
            "발주 스케줄입니다",
            "군자로 43",
            LocalDateTime.of(2024, 7, 5, 10, 0),
            LocalDateTime.of(2024, 7, 5, 23, 59),
            BoolType.T,
            BoolType.F,
            "colorHexInfo",
            LocalDateTime.of(2024, 7, 1, 2, 3),
            LocalDateTime.of(2024, 7, 1, 2, 3)
    );

    public static BusinessSchedule BS_3 = BusinessSchedule.of(
            102L,
            2L,
            519L,
            ScheduleType.ORDER,
            "발주 스케줄입니다",
            "군자로 43",
            LocalDateTime.of(2024, 7, 5, 10, 0),
            LocalDateTime.of(2024, 7, 5, 23, 59),
            BoolType.F,
            BoolType.F,
            "colorHexInfo",
            LocalDateTime.of(2024, 7, 1, 2, 3),
            LocalDateTime.of(2024, 7, 1, 2, 3)
    );

    public static BusinessSchedule BS_4 = BusinessSchedule.of(
            103L,
            2L,
            519L,
            ScheduleType.ORDER,
            "발주 스케줄입니다",
            "군자로 43",
            LocalDateTime.of(2024, 7, 5, 10, 0),
            LocalDateTime.of(2024, 7, 5, 23, 59),
            BoolType.T,
            BoolType.T,
            "colorHexInfo",
            LocalDateTime.of(2024, 7, 1, 2, 3),
            LocalDateTime.of(2024, 7, 1, 2, 3)
    );
}
