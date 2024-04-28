package com.interior.domain.business.material;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.interior.domain.business.expense.BusinessMaterialExpense;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessMaterial {

    private Long id;

    private Long businessId;

    private String name;

    private String usageCategory;

    private String category;

    private BigDecimal amount;

    private String unit;

    private String memo;

    private BusinessMaterialExpense businessMaterialExpense;

    private LocalDateTime lastModified;
    private LocalDateTime createdAt;

    private BusinessMaterial(
            final Long id,
            final Long businessId,
            final String name,
            final String usageCategory,
            final String category,
            final BigDecimal amount,
            final String unit,
            final String memo,
            final BusinessMaterialExpense businessMaterialExpense,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.businessId = businessId;
        this.name = name;
        this.usageCategory = usageCategory;
        this.category = category;
        this.amount = amount;
        this.unit = unit;
        this.memo = memo;
        this.businessMaterialExpense = businessMaterialExpense;
        this.lastModified = lastModified;
        this.createdAt = createdAt;
    }

    public static BusinessMaterial of(
            final Long id,
            final Long businessId,
            final String name,
            final String usageCategory,
            final String category,
            final BigDecimal amount,
            final String unit,
            final String memo,
            final BusinessMaterialExpense businessMaterialExpense
    ) {
        return new BusinessMaterial(
                id,
                businessId,
                name,
                usageCategory,
                category,
                amount,
                unit,
                memo,
                businessMaterialExpense,
                LocalDateTime.now(), LocalDateTime.now());
    }

    public static BusinessMaterial of(
            final Long businessId,
            final String name,
            final String usageCategory,
            final String category,
            final BigDecimal amount,
            final String unit,
            final String memo
    ) {
        return new BusinessMaterial(
                null,
                businessId,
                name,
                usageCategory,
                category,
                amount,
                unit,
                memo,
                null,
                LocalDateTime.now(), LocalDateTime.now());
    }
}
