package com.interior.util.converter.jpa.business;

import com.interior.adapter.outbound.jpa.entity.business.BusinessEntity;
import com.interior.adapter.outbound.jpa.entity.business.material.BusinessMaterialEntity;
import com.interior.domain.business.Business;
import com.interior.domain.business.material.BusinessMaterial;
import java.util.stream.Collectors;

public class BusinessEntityConverter {

    public static BusinessEntity businessToEntity(final Business business) {
        return BusinessEntity.of(
                business.getName(),
                business.getCompanyId(),
                business.getCustomerId(),
                business.getStatus(),
                business.getBusinessMaterialList().stream()
                        .map(BusinessEntityConverter::businessMaterialToEntity)
                        .collect(Collectors.toList())
        );
    }

    public static BusinessMaterialEntity businessMaterialToEntity(final BusinessMaterial businessMaterial) {
        return BusinessMaterialEntity.of(
                businessMaterial.getBusinessId(),
                businessMaterial.getName(),
                businessMaterial.getUsageCategory(),
                businessMaterial.getCategory(),
                businessMaterial.getAmount(),
                businessMaterial.getUnit(),
                businessMaterial.getMemo()
        );
    }
}
