package com.interior.domain.schedule.repository;

import com.interior.domain.schedule.BusinessSchedule;
import com.interior.domain.schedule.BusinessScheduleAlarm;
import java.util.List;

public interface BusinessScheduleRepository {

    void createSchedule(final List<BusinessSchedule> businessScheduleList);

    void createAlarmRelatedToSchedule(final List<BusinessScheduleAlarm> businessScheduleAlarmList);

    List<BusinessSchedule> findAllByBusinessId(final Long businessId);
}
