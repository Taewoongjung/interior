package com.interior.adapter.outbound.jpa.repository.business;

import com.interior.adapter.outbound.jpa.entity.business.BusinessEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessJpaRepository extends JpaRepository<BusinessEntity, Long> {

    List<BusinessEntity> findBusinessesEntityByCompanyId(final Long companyId);
}
