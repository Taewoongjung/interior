package com.interior.domain.schedule.repository;

import com.interior.domain.schedule.BusinessSchedule;
import com.interior.domain.schedule.BusinessScheduleAlarm;
import java.util.List;

public interface BusinessScheduleRepository {

    BusinessSchedule createSchedule(final BusinessSchedule businessSchedule);

    BusinessScheduleAlarm createAlarmRelatedToSchedule(
            final BusinessScheduleAlarm businessScheduleAlarm);

    List<BusinessSchedule> findAllByBusinessId(final List<Long> businessId);
}
