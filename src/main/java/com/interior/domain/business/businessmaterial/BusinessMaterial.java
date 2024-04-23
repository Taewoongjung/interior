package com.interior.domain.business.businessmaterial;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    private int amount;

    private String unit;

    private String memo;

    private LocalDateTime lastModified;
    private LocalDateTime createdAt;

    private BusinessMaterial(
            final Long id,
            final Long businessId,
            final String name,
            final String usageCategory,
            final String category,
            final int amount,
            final String unit,
            final String memo,
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
        this.lastModified = lastModified;
        this.createdAt = createdAt;
    }

    public static BusinessMaterial of(
            final Long id,
            final Long businessId,
            final String name,
            final String usageCategory,
            final String category,
            final int amount,
            final String unit,
            final String memo
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
                LocalDateTime.now(), LocalDateTime.now());
    }

    public static BusinessMaterial of(
            final Long businessId,
            final String name,
            final String usageCategory,
            final String category,
            final int amount,
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
                LocalDateTime.now(), LocalDateTime.now());
    }
}
