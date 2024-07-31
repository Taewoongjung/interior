package com.interior.helper.spy;

import static businessschedule.BusinessScheduleAlarmFixture.BSA_1;
import static businessschedule.BusinessScheduleAlarmFixture.BSA_2;
import static businessschedule.BusinessScheduleFixture.BS_1;
import static businessschedule.BusinessScheduleFixture.BS_2;
import static businessschedule.BusinessScheduleFixture.BS_3;
import static businessschedule.BusinessScheduleFixture.BS_4;

import com.interior.domain.schedule.BusinessSchedule;
import com.interior.domain.schedule.BusinessScheduleAlarm;
import com.interior.domain.schedule.repository.BusinessScheduleRepository;
import com.interior.domain.schedule.repository.dto.ReviseBusinessSchedule;
import java.util.ArrayList;
import java.util.List;

public class BusinessScheduleRepositorySpy implements BusinessScheduleRepository {

    @Override
    public BusinessSchedule findById(Long businessScheduleId) {
        return null;
    }

    @Override
    public BusinessSchedule createSchedule(BusinessSchedule businessSchedule) {
        // save
        return BS_1; // 알람과 함께 설정 시 스케줄 생성이 선행 돼야 해서 임의 BusinessSchedule 객체 return
    }

    @Override
    public BusinessScheduleAlarm createAlarmRelatedToSchedule(
            BusinessScheduleAlarm businessScheduleAlarm) {
        // entity save
        return businessScheduleAlarm;
    }

    private List<BusinessSchedule> getBusinessScheduleList() {
        List<BusinessSchedule> list = new ArrayList<>();
        list.add(BS_1);
        list.add(BS_2);
        list.add(BS_3);
        list.add(BS_4);

        return list;
    }

    private List<BusinessScheduleAlarm> getBusinessScheduleAlarmList() {
        List<BusinessScheduleAlarm> businessScheduleAlarmList = new ArrayList<>();
        businessScheduleAlarmList.add(BSA_1);
        businessScheduleAlarmList.add(BSA_2);

        return businessScheduleAlarmList;
    }

    @Override
    public List<BusinessSchedule> findAllByBusinessId(List<Long> businessId) {
        return null;
    }

    @Override
    public List<BusinessScheduleAlarm> findAllScheduleAlarmByBusinessScheduleIdList(
            List<Long> businessScheduleIdList) {
        return null;
    }

    @Override
    public Long reviseBusinessSchedule(ReviseBusinessSchedule businessSchedule) {
        return null;
    }
}
