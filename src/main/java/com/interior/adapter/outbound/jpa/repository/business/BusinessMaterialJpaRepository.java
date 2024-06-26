package com.interior.adapter.outbound.jpa.repository.business;

import com.interior.adapter.outbound.jpa.entity.business.material.BusinessMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessMaterialJpaRepository extends JpaRepository<BusinessMaterialEntity, Long> {

}
