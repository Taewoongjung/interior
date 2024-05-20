package com.interior.adapter.outbound.jpa.repository.business;

import com.interior.adapter.outbound.jpa.entity.business.material.log.BusinessMaterialLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessMaterialLogJpaRepository extends
        JpaRepository<BusinessMaterialLogEntity, Long> {

}
