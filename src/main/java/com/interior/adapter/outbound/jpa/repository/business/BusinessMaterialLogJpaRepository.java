package com.interior.adapter.outbound.jpa.repository.business;

import com.interior.adapter.outbound.jpa.entity.business.material.log.BusinessMaterialLogEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessMaterialLogJpaRepository extends
        JpaRepository<BusinessMaterialLogEntity, Long> {

    List<BusinessMaterialLogEntity> findAllByBusinessId(final Long businessId);
}
