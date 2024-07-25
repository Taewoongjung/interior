package com.interior.adapter.outbound.jpa.repository.schedule;

import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS_SCHEDULE;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS_SCHEDULE_RELATED_TO_THE_BUSINESS;
import static com.interior.util.CheckUtil.check;
import static com.interior.util.converter.jpa.schedule.BusinessScheduleEntityConverter.businessScheduleAlarmToEntity;
import static com.interior.util.converter.jpa.schedule.BusinessScheduleEntityConverter.businessScheduleToEntity;

import com.interior.adapter.outbound.jpa.entity.schedule.BusinessScheduleAlarmEntity;
import com.interior.adapter.outbound.jpa.entity.schedule.BusinessScheduleEntity;
import com.interior.domain.schedule.BusinessSchedule;
import com.interior.domain.schedule.BusinessScheduleAlarm;
import com.interior.domain.schedule.repository.BusinessScheduleRepository;
import com.interior.domain.schedule.repository.dto.ReviseBusinessSchedule;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class BusinessScheduleRepositoryAdapter implements BusinessScheduleRepository {

    private final BusinessScheduleJpaRepository scheduleJpaRepository;
    private final BusinessScheduleAlarmJpaRepository scheduleAlarmJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public BusinessSchedule findById(final Long businessScheduleId) {

        BusinessScheduleEntity entity = getBusinessScheduleEntity(businessScheduleId);

        return entity.toPojo();
    }

    private BusinessScheduleEntity getBusinessScheduleEntity(final Long businessScheduleId) {
        return scheduleJpaRepository.findById(businessScheduleId)
                .orElseThrow(
                        () -> new NoSuchElementException(NOT_EXIST_BUSINESS_SCHEDULE.getMessage()));
    }

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

        check(businessScheduleEntityList == null,
                NOT_EXIST_BUSINESS_SCHEDULE_RELATED_TO_THE_BUSINESS);

        if (businessScheduleEntityList.size() == 0) {
            return new ArrayList<>();
        }

        return businessScheduleEntityList.stream()
                .map(BusinessScheduleEntity::toPojo).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessScheduleAlarm> findAllScheduleAlarmByBusinessScheduleIdList(
            List<Long> businessScheduleIdList) {

        List<BusinessScheduleAlarmEntity> entityList = scheduleAlarmJpaRepository.findAllByBusinessScheduleIdIn(
                businessScheduleIdList);

        return entityList.stream().map(BusinessScheduleAlarmEntity::toPojo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long reviseBusinessSchedule(final ReviseBusinessSchedule reviseReq) {

        BusinessScheduleEntity businessScheduleEntity = getBusinessScheduleEntity(
                reviseReq.scheduleId());

        businessScheduleEntity.setBusinessId(reviseReq.relatedBusinessId());
        businessScheduleEntity.setType(reviseReq.scheduleType());
        businessScheduleEntity.setTitle(reviseReq.title());
        businessScheduleEntity.setOrderingPlace(reviseReq.orderingPlace());
        businessScheduleEntity.setStartDate(reviseReq.startDate());
        businessScheduleEntity.setEndDate(reviseReq.endDate());
        businessScheduleEntity.setIsAlarmOn(reviseReq.isAlarmOn());
        businessScheduleEntity.setColorHexInfo(reviseReq.colorHexInfo());

        return businessScheduleEntity.getId();
    }
}
