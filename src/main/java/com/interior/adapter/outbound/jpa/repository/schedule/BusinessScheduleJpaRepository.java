package com.interior.adapter.outbound.jpa.repository.schedule;

import com.interior.adapter.outbound.jpa.entity.schedule.BusinessScheduleEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessScheduleJpaRepository extends JpaRepository<BusinessScheduleEntity, Long> {

    List<BusinessScheduleEntity> findAllByBusinessId(final Long businessId);
}
