package com.interior.util.converter.jpa.business;

import com.interior.adapter.outbound.jpa.entity.business.BusinessEntity;
import com.interior.adapter.outbound.jpa.entity.business.businessmaterial.BusinessMaterialEntity;
import com.interior.domain.business.Business;
import com.interior.domain.business.businessmaterial.BusinessMaterial;
import java.util.stream.Collectors;

public class BusinessEntityConverter {

    public static BusinessEntity businessToEntity(final Business business) {
        return BusinessEntity.of(
                business.getName(),
                business.getHostId(),
                business.getCustomerId(),
                business.getStatus(),
                business.getBusinessMaterialList().stream()
                        .map(BusinessEntityConverter::businessToEntity)
                        .collect(Collectors.toList())
        );
    }

    public static BusinessMaterialEntity businessToEntity(final BusinessMaterial businessMaterial) {
        return BusinessMaterialEntity.of(
                businessMaterial.getBusinessId(),
                businessMaterial.getName(),
                businessMaterial.getCategory(),
                businessMaterial.getAmount(),
                businessMaterial.getMemo()
        );
    }
}
