package businessschedule;

import com.interior.domain.schedule.BusinessScheduleAlarm;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;

public class BusinessScheduleAlarmFixture {

    public static BusinessScheduleAlarm BSA_1 = BusinessScheduleAlarm.of(
            1L,
            101L,
            LocalDateTime.of(2024, 7, 5, 8, 0),
            BoolType.F,
            BoolType.F,
            null,
            LocalDateTime.of(2024, 7, 1, 2, 3),
            LocalDateTime.of(2024, 7, 1, 2, 3)
    );

    public static BusinessScheduleAlarm BSA_2 = BusinessScheduleAlarm.of(
            2L,
            102L,
            LocalDateTime.of(2024, 7, 5, 8, 0),
            BoolType.F,
            BoolType.F,
            null,
            LocalDateTime.of(2024, 7, 1, 2, 3),
            LocalDateTime.of(2024, 7, 1, 2, 3)
    );
}
