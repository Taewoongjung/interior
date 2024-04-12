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

    private String category;

    private int amount;

    private String memo;

    private LocalDateTime lastModified;
    private LocalDateTime createdAt;

    private BusinessMaterial(
            final Long id,
            final Long businessId,
            final String name,
            final String category,
            final int amount,
            final String memo,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.businessId = businessId;
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.memo = memo;
        this.lastModified = lastModified;
        this.createdAt = createdAt;
    }

    public static BusinessMaterial of(
            final Long id,
            final Long businessId,
            final String name,
            final String category,
            final int amount,
            final String memo
    ) {
        return new BusinessMaterial(
                id,
                businessId,
                name,
                category,
                amount,
                memo,
                LocalDateTime.now(), LocalDateTime.now());
    }

    public static BusinessMaterial of(
            final Long businessId,
            final String name,
            final String category,
            final int amount,
            final String memo
    ) {
        return new BusinessMaterial(
                null,
                businessId,
                name,
                category,
                amount,
                memo,
                LocalDateTime.now(), LocalDateTime.now());
    }
}
