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
import java.util.ArrayList;
import java.util.List;

public class BusinessScheduleRepositorySpy implements BusinessScheduleRepository {

    @Override
    public BusinessSchedule createSchedule(BusinessSchedule businessSchedule) {
        // saveAll
        return null;
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
}
