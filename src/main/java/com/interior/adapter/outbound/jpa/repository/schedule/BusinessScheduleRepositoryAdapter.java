package com.interior.adapter.outbound.jpa.repository.schedule;

import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS_SCHEDULE;
import static com.interior.util.CheckUtil.check;
import static com.interior.util.converter.jpa.schedule.BusinessScheduleEntityConverter.businessScheduleAlarmToEntity;
import static com.interior.util.converter.jpa.schedule.BusinessScheduleEntityConverter.businessScheduleToEntity;

import com.interior.adapter.outbound.jpa.entity.schedule.BusinessScheduleAlarmEntity;
import com.interior.adapter.outbound.jpa.entity.schedule.BusinessScheduleEntity;
import com.interior.domain.schedule.BusinessSchedule;
import com.interior.domain.schedule.BusinessScheduleAlarm;
import com.interior.domain.schedule.repository.BusinessScheduleRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class BusinessScheduleRepositoryAdapter implements BusinessScheduleRepository {

    private final BusinessScheduleJpaRepository scheduleJpaRepository;
    private final BusinessScheduleAlarmJpaRepository scheduleAlarmJpaRepository;

    @Override
    @Transactional
    public BusinessSchedule createSchedule(final BusinessSchedule businessSchedule) {

        BusinessScheduleEntity entity = scheduleJpaRepository.save(
                businessScheduleToEntity(businessSchedule));

        return entity.toPojo();
    }

    @Override
    @Transactional
    public BusinessScheduleAlarm createAlarmRelatedToSchedule(
            final BusinessScheduleAlarm businessScheduleAlarm) {

        BusinessScheduleAlarmEntity entity = scheduleAlarmJpaRepository.save(
                businessScheduleAlarmToEntity(businessScheduleAlarm));

        return entity.toPojo();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessSchedule> findAllByBusinessId(final List<Long> businessId) {

        List<BusinessScheduleEntity> businessScheduleEntityList =
                scheduleJpaRepository.findAllByBusinessIdIn(businessId);

        check(businessScheduleEntityList == null, NOT_EXIST_BUSINESS_SCHEDULE);

        if (businessScheduleEntityList.size() == 0) {
            return new ArrayList<>();
        }

        return businessScheduleEntityList.stream()
                .map(BusinessScheduleEntity::toPojo).toList();
    }


}
