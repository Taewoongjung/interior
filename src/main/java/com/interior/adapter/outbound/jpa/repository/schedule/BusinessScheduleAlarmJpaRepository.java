package com.interior.adapter.outbound.jpa.repository.schedule;

import com.interior.adapter.outbound.jpa.entity.schedule.BusinessScheduleAlarmEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessScheduleAlarmJpaRepository extends
        JpaRepository<BusinessScheduleAlarmEntity, Long> {

    List<BusinessScheduleAlarmEntity> findAllByBusinessScheduleIdInAndIsDeleted_F(
            final List<Long> BusinessScheduleIdList);
}
