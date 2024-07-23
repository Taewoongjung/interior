package com.interior.domain.schedule;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_ALARM_START_DATE_IN_SCHEDULE_ALARM;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_SCHEDULE_ID_IN_SCHEDULE_ALARM;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_DELETED_IN_SCHEDULE_ALARM;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_SUCCESS_IN_SCHEDULE_ALARM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.adapter.inbound.schedule.webdto.AlarmTime;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BusinessScheduleAlarm 은 ")
class BusinessScheduleAlarmTest {

    @Test
    @DisplayName("생선된다.")
    void test1() {
        assertDoesNotThrow(() -> BusinessScheduleAlarm.of(
                10L,
                LocalDateTime.of(2050, 5, 19, 23, 30),
                BoolType.T,
                BoolType.T,
                AlarmTime.A_DAY_AGO
        ));
    }

    @Test
    @DisplayName("연관 스케줄의 id는 필수값입니다.")
    void test2() {
        assertThatThrownBy(() -> {
            BusinessScheduleAlarm.of(
                    null,
                    LocalDateTime.of(2024, 5, 19, 23, 30),
                    BoolType.T,
                    BoolType.T,
                    AlarmTime.A_DAY_AGO
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_BUSINESS_SCHEDULE_ID_IN_SCHEDULE_ALARM.getMessage());
    }

    @Test
    @DisplayName("알람시간은 필수값입니다.")
    void test3() {
        assertThatThrownBy(() -> {
            BusinessScheduleAlarm.of(
                    10L,
                    null,
                    BoolType.T,
                    BoolType.T,
                    AlarmTime.A_DAY_AGO
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_ALARM_START_DATE_IN_SCHEDULE_ALARM.getMessage());
    }

    @Test
    @DisplayName("알람 성공여부값은 필수값입니다.")
    void test4() {
        assertThatThrownBy(() -> {
            BusinessScheduleAlarm.of(
                    10L,
                    LocalDateTime.of(2050, 5, 19, 23, 30),
                    null,
                    BoolType.T,
                    AlarmTime.A_DAY_AGO
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_IS_SUCCESS_IN_SCHEDULE_ALARM.getMessage());
    }

    @Test
    @DisplayName("알람 삭제여부값은 필수값입니다.")
    void test5() {
        assertThatThrownBy(() -> {
            BusinessScheduleAlarm.of(
                    10L,
                    LocalDateTime.of(2050, 5, 19, 23, 30),
                    BoolType.T,
                    null,
                    AlarmTime.A_DAY_AGO
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_IS_DELETED_IN_SCHEDULE_ALARM.getMessage());
    }
}