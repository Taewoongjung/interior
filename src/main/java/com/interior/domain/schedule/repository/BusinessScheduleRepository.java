package com.interior.domain.schedule.repository;

import com.interior.domain.schedule.BusinessSchedule;
import com.interior.domain.schedule.BusinessScheduleAlarm;
import com.interior.domain.schedule.repository.dto.ReviseBusinessSchedule;
import java.util.List;

public interface BusinessScheduleRepository {

    BusinessSchedule findById(final Long businessScheduleId);

    BusinessSchedule createSchedule(final BusinessSchedule businessSchedule);

    BusinessScheduleAlarm createAlarmRelatedToSchedule(
            final BusinessScheduleAlarm businessScheduleAlarm);

    List<BusinessSchedule> findAllByBusinessId(final List<Long> businessId);

    List<BusinessScheduleAlarm> findAllScheduleAlarmByBusinessScheduleIdList(
            final List<Long> businessScheduleIdList);

    Long reviseBusinessSchedule(final ReviseBusinessSchedule businessSchedule);
}
