package com.interior.domain.business.repository.dto;

public record CreateBusinessMaterial(
        Long businessId,
        String name,
        String usageCategory,
        String category,
        Integer amount,
        String unit,
        String memo,
        String materialCostPerUnit,
        String laborCostPerUnit
) { }
