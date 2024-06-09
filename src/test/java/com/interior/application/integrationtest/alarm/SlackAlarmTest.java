package com.interior.application.integrationtest.alarm;

import com.interior.adapter.outbound.alarm.AlarmService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class SlackAlarmTest {

    @Autowired
    private AlarmService alarmService;

    @Test
    public void 알림테스트() {
//        alarmService.sendNewBusinessAlarm("사업명", "회사이름", "사람이름", "이메일", "전화번호");
//        alarmService.sendErrorAlarm("에러메시지", "테스트");
    }
}
