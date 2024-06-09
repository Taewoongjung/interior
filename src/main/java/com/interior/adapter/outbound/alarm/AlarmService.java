package com.interior.adapter.outbound.alarm;

import com.interior.adapter.outbound.alarm.dto.event.ErrorAlarm;
import com.interior.adapter.outbound.alarm.dto.event.NewBusinessAlarm;
import com.interior.adapter.outbound.alarm.dto.event.NewCompanyAlarm;
import com.interior.adapter.outbound.alarm.dto.event.NewUserAlarm;

public interface AlarmService {

    void sendErrorAlarm(final ErrorAlarm event);

    void sendNewUserAlarm(final NewUserAlarm event);

    void sendNewCompanyAlarm(final NewCompanyAlarm event);

    void sendNewBusinessAlarm(final NewBusinessAlarm event);
}
