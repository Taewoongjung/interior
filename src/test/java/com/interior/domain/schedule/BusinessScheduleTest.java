package com.interior.domain.schedule;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_ID_IN_SCHEDULE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_END_DATE_IN_SCHEDULE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_ALARM_ON_IN_SCHEDULE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_DELETED;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_START_DATE_IN_SCHEDULE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_TYPE_IN_SCHEDULE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_USER_ID_IN_SCHEDULE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BusinessSchedule 는 ")
class BusinessScheduleTest {

    @Test
    @DisplayName("생성된다. (일정)")
    void test1() {
        assertDoesNotThrow(() -> BusinessSchedule.of(
                10L,
                519L,
                ScheduleType.WORK,
                "일정 스케줄 등록 ~",
                null,
                LocalDateTime.of(2024, 5, 19, 23, 30),
                LocalDateTime.of(2024, 5, 19, 23, 50),
                BoolType.F,
                "colorHexInfo"
        ));
    }

    @Test
    @DisplayName("생성된다. (발주)")
    void test2() {
        assertDoesNotThrow(() -> BusinessSchedule.of(
                10L,
                519L,
                ScheduleType.ORDER,
                "발주 스케줄 등록 ~",
                "양주시 머선머선면 머선머선 주소",
                LocalDateTime.of(2024, 5, 19, 23, 30),
                null,
                BoolType.T,
                "colorHexInfo"
        ));
    }

    @Test
    @DisplayName("연관 사업 id는 필수값입니다.")
    void test3() {
        assertThatThrownBy(() -> {
            BusinessSchedule.of(
                    null,
                    519L,
                    ScheduleType.ORDER,
                    "발주 스케줄 등록 ~",
                    "양주시 머선머선면 머선머선 주소",
                    LocalDateTime.of(2024, 5, 19, 23, 30),
                    null,
                    BoolType.T,
                    "colorHexInfo"
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_BUSINESS_ID_IN_SCHEDULE.getMessage());
    }

    @Test
    @DisplayName("등록한 사람의 id는 필수값입니다.")
    void test4() {
        assertThatThrownBy(() -> {
            BusinessSchedule.of(
                    1L,
                    null,
                    ScheduleType.ORDER,
                    "발주 스케줄 등록 ~",
                    "양주시 머선머선면 머선머선 주소",
                    LocalDateTime.of(2024, 5, 19, 23, 30),
                    null,
                    BoolType.T,
                    "colorHexInfo"
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_USER_ID_IN_SCHEDULE.getMessage());
    }

    @Test
    @DisplayName("스케줄 타입은 필수값입니다.")
    void test5() {
        assertThatThrownBy(() -> {
            BusinessSchedule.of(
                    1L,
                    519L,
                    null,
                    "발주 스케줄 등록 ~",
                    "양주시 머선머선면 머선머선 주소",
                    LocalDateTime.of(2024, 5, 19, 23, 30),
                    null,
                    BoolType.T,
                    "colorHexInfo"
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_TYPE_IN_SCHEDULE.getMessage());
    }

    @Test
    @DisplayName("스케줄 시작(startDate)값은 필수값입니다.")
    void test6() {
        assertThatThrownBy(() -> {
            BusinessSchedule.of(
                    1L,
                    519L,
                    ScheduleType.ORDER,
                    "발주 스케줄 등록 ~",
                    "양주시 머선머선면 머선머선 주소",
                    null,
                    null,
                    BoolType.T,
                    "colorHexInfo"
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_START_DATE_IN_SCHEDULE.getMessage());
    }

    @Test
    @DisplayName("일정 타입일 때 끝나는(endDate)값은 필수값입니다.")
    void test7() {
        assertThatThrownBy(() -> {
            BusinessSchedule.of(
                    1L,
                    519L,
                    ScheduleType.WORK,
                    "발주 스케줄 등록 ~",
                    "양주시 머선머선면 머선머선 주소",
                    LocalDateTime.of(2024, 5, 19, 23, 30),
                    null,
                    BoolType.T,
                    "colorHexInfo"
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_END_DATE_IN_SCHEDULE.getMessage());
    }

    @Test
    @DisplayName("알람 켜짐 여부는 필수값입니다.")
    void test8() {
        assertThatThrownBy(() -> {
            BusinessSchedule.of(
                    1L,
                    519L,
                    ScheduleType.ORDER,
                    "발주 스케줄 등록 ~",
                    "양주시 머선머선면 머선머선 주소",
                    LocalDateTime.of(2024, 5, 19, 23, 30),
                    null,
                    null,
                    "colorHexInfo"
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_IS_ALARM_ON_IN_SCHEDULE.getMessage());
    }

    @Test
    @DisplayName("삭제 여부는 필수값입니다.")
    void test9() {
        assertThatThrownBy(() -> {
            BusinessSchedule.of(
                    10L,
                    1L,
                    519L,
                    ScheduleType.ORDER,
                    "발주 스케줄 등록 ~",
                    "양주시 머선머선면 머선머선 주소",
                    LocalDateTime.of(2024, 5, 19, 23, 30),
                    null,
                    BoolType.T,
                    null,
                    "colorHexInfo",
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_IS_DELETED.getMessage());
    }
}