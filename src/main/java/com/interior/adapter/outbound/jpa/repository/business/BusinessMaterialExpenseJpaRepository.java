package com.interior.adapter.outbound.jpa.repository.business;

import com.interior.adapter.outbound.jpa.entity.business.expense.BusinessMaterialExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessMaterialExpenseJpaRepository extends
        JpaRepository<BusinessMaterialExpenseEntity, Long> {

}
