package com.interior.adapter.outbound.jpa.repository.schedule;

import com.interior.adapter.outbound.jpa.entity.schedule.BusinessScheduleAlarmEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BusinessScheduleAlarmJpaRepository extends
        JpaRepository<BusinessScheduleAlarmEntity, Long> {

    @Query("SELECT b FROM BusinessScheduleAlarmEntity b WHERE b.businessScheduleId IN :businessScheduleIdList AND b.isDeleted = 'F'")
    List<BusinessScheduleAlarmEntity> findAllByBusinessScheduleIdInAndIsNotDeleted(
            final List<Long> businessScheduleIdList);
}
