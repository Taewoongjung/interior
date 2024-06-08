package com.interior.adapter.outbound.jpa.repository.business;

import com.interior.adapter.outbound.jpa.entity.business.log.BusinessLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessLogJpaRepository extends JpaRepository<BusinessLogEntity, Long> {

}
