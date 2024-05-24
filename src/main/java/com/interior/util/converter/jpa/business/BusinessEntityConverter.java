package com.interior.util.converter.jpa.business;

import com.interior.adapter.outbound.jpa.entity.business.BusinessEntity;
import com.interior.adapter.outbound.jpa.entity.business.expense.BusinessMaterialExpenseEntity;
import com.interior.adapter.outbound.jpa.entity.business.material.BusinessMaterialEntity;
import com.interior.adapter.outbound.jpa.entity.business.material.log.BusinessMaterialLogEntity;
import com.interior.domain.business.Business;
import com.interior.domain.business.expense.BusinessMaterialExpense;
import com.interior.domain.business.log.BusinessMaterialLog;
import com.interior.domain.business.material.BusinessMaterial;
import java.util.stream.Collectors;

public class BusinessEntityConverter {

    public static BusinessEntity businessToEntity(final Business business) {
        return BusinessEntity.of(
                business.getName(),
                business.getCompanyId(),
                business.getCustomerId(),
                business.getStatus(),
                business.getStatusDetail(),
                business.getBusinessMaterialList().stream()
                        .map(BusinessEntityConverter::businessMaterialToEntity)
                        .collect(Collectors.toList())
        );
    }

    public static BusinessMaterialEntity businessMaterialToEntity(
            final BusinessMaterial businessMaterial) {
        return BusinessMaterialEntity.of(
                businessMaterial.getBusinessId(),
                businessMaterial.getName(),
                businessMaterial.getUsageCategory(),
                businessMaterial.getCategory(),
                businessMaterial.getAmount(),
                businessMaterial.getUnit(),
                businessMaterial.getMemo(),
                businessMaterial.getIsDeleted(),
                businessMaterial.getBusinessMaterialExpense() != null ?
                        businessMaterialExpenseToEntity(
                                businessMaterial.getBusinessMaterialExpense()) : null
        );
    }

    private static BusinessMaterialExpenseEntity businessMaterialExpenseToEntity(
            final BusinessMaterialExpense businessMaterialExpense) {

        return BusinessMaterialExpenseEntity.of(
                businessMaterialExpense.getBusinessMaterialId(),
                businessMaterialExpense.getMaterialCostPerUnit(),
                businessMaterialExpense.getLaborCostPerUnit()
        );
    }

    public static BusinessMaterialLogEntity businessMaterialLogToEntity(
            final BusinessMaterialLog businessMaterialLog) {

        return BusinessMaterialLogEntity.of(
                businessMaterialLog.getBusinessMaterialId(),
                businessMaterialLog.getChangeField(),
                businessMaterialLog.getBeforeData(),
                businessMaterialLog.getAfterData(),
                businessMaterialLog.getUpdater(),
                businessMaterialLog.getUpdaterName()
        );
    }
}
