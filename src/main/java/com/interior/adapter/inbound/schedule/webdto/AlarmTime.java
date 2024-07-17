package com.interior.adapter.inbound.schedule.webdto;

import java.time.LocalDateTime;

public enum AlarmTime {

    A_WEEK_AGO,
    FIVE_DAYS_AGO,
    TWO_DAYS_AGO,
    A_DAY_AGO,
    FIVE_HOURS_AGO,
    TWO_HOURS_AGO,
    A_HOUR_AGO,
    THIRTY_M_AGO,
    FIFTY_M_AGO,
    TEN_M_AGO,
    FIVE_M_AGO;

    public LocalDateTime getAlarmTime(final LocalDateTime startTime) {

        if (this.equals(FIVE_M_AGO)) {
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
