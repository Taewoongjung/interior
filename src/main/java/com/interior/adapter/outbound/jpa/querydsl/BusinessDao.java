package com.interior.adapter.outbound.jpa.querydsl;

import com.interior.adapter.outbound.excel.BusinessMaterialExcelDownload;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.interior.adapter.outbound.jpa.entity.business.QBusinessEntity.businessEntity;
import static com.interior.adapter.outbound.jpa.entity.business.material.QBusinessMaterialEntity.businessMaterialEntity;
import static com.interior.adapter.outbound.jpa.entity.business.expense.QBusinessMaterialExpenseEntity.businessMaterialExpenseEntity;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BusinessDao {

    private final JPAQueryFactory queryFactory;

    public List<BusinessMaterialExcelDownload> getBusinessMaterialList(final long businessId) {
        return queryFactory.select(
                Projections.constructor(
                        (BusinessMaterialExcelDownload.class),
                        businessMaterialEntity.category,
                        businessMaterialEntity.name,
                        businessMaterialEntity.amount,
                        businessMaterialEntity.unit,
                        businessMaterialExpenseEntity.materialCostPerUnit,
                        // allMaterialCostPerUnit
                        businessMaterialExpenseEntity.laborCostPerUnit
                        // allLaborCostPerUnit
                        // totalUnitPrice
                        // totalPrice
                )
                )
                .from(businessEntity)
                .join(businessEntity.businessMaterialList, businessMaterialEntity)
                .join(businessMaterialEntity.businessMaterialExpense, businessMaterialExpenseEntity)
                .where(businessEntity.id.eq(businessId))
                .fetch();
    }
}
