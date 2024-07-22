package com.interior.helper.spy;

import static businessschedule.BusinessScheduleFixture.BS_1;
import static businessschedule.BusinessScheduleFixture.BS_2;
import static businessschedule.BusinessScheduleFixture.BS_3;
import static businessschedule.BusinessScheduleFixture.BS_4;
import static com.interior.adapter.common.exception.ErrorType.ALREADY_EXIST_ALARM_INFO_OF_THE_SCHEDULE;
import static com.interior.util.CheckUtil.check;

import com.interior.domain.schedule.BusinessSchedule;
import com.interior.domain.schedule.BusinessScheduleAlarm;
import com.interior.domain.schedule.repository.BusinessScheduleRepository;
import com.interior.domain.util.BoolType;
import java.util.ArrayList;
import java.util.List;

public class BusinessScheduleRepositorySpy implements BusinessScheduleRepository {

    @Override
    public void createSchedule(List<BusinessSchedule> businessScheduleList) {
        // saveAll
    }

    @Override
    public void createAlarmRelatedToSchedule(
            List<BusinessScheduleAlarm> businessScheduleAlarmList) {

        List<Long> creatingIdListOfBusinessSchedule = businessScheduleAlarmList.stream()
                .map(BusinessScheduleAlarm::getBusinessScheduleId)
                .toList();

        List<BusinessSchedule> businessScheduleList = getBusinessScheduleList();

        BusinessSchedule foundSchedule = businessScheduleList.stream()
                .filter(f -> creatingIdListOfBusinessSchedule.contains(f.getBusinessId())
                        && BoolType.F.equals(f.getIsDeleted()))
                .findFirst()
                .orElse(null);

        check(foundSchedule != null, ALREADY_EXIST_ALARM_INFO_OF_THE_SCHEDULE);

    }

    private List<BusinessSchedule> getBusinessScheduleList() {
        List<BusinessSchedule> list = new ArrayList<>();
        list.add(BS_1);
        list.add(BS_2);
        list.add(BS_3);
        list.add(BS_4);

        return list;
    }

    @Override
    public List<BusinessSchedule> findAllByBusinessId(List<Long> businessId) {
        return null;
    }
}
