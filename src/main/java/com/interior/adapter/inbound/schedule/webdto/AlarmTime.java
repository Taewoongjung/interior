package com.interior.adapter.inbound.schedule.webdto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmTime {

    A_WEEK_AGO("1주일 전"),
    FIVE_DAYS_AGO("5일 전"),
    TWO_DAYS_AGO("2일 전"),
    A_DAY_AGO("하루 전"),
    FIVE_HOURS_AGO("5시간 전"),
    TWO_HOURS_AGO("2시간 전"),
    A_HOUR_AGO("1시간 전"),
    THIRTY_M_AGO("30분 전"),
    FIFTY_M_AGO("15분 전"),
    TEN_M_AGO("10분 전"),
    FIVE_M_AGO("5분 전"),
    AT_THE_TIME("");

    private final String type;

    public LocalDateTime getAlarmTime(final LocalDateTime startTime) {

        if (this.equals(AT_THE_TIME)) {
            return startTime;
        } else if (this.equals(FIVE_M_AGO)) {
            return startTime.minusMinutes(5L);
        } else if (this.equals(TEN_M_AGO)) {
            return startTime.minusMinutes(10L);
        } else if (this.equals(FIFTY_M_AGO)) {
            return startTime.minusMinutes(15L);
        } else if (this.equals(THIRTY_M_AGO)) {
            return startTime.minusMinutes(30L);
        } else if (this.equals(A_HOUR_AGO)) {
            return startTime.minusHours(1L);
        } else if (this.equals(TWO_HOURS_AGO)) {
            return startTime.minusHours(2L);
        } else if (this.equals(FIVE_HOURS_AGO)) {
            return startTime.minusHours(5L);
        } else if (this.equals(A_DAY_AGO)) {
            return startTime.minusDays(1L);
        } else if (this.equals(TWO_DAYS_AGO)) {
            return startTime.minusDays(2L);
        } else if (this.equals(FIVE_DAYS_AGO)) {
            return startTime.minusDays(5L);
        } else {
            return startTime.minusDays(7L);
        }
    }

}
