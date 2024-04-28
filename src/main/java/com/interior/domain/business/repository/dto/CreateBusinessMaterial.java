package com.interior.domain.business.repository.dto;

import java.math.BigDecimal;

public record CreateBusinessMaterial(
        Long businessId,
        String name,
        String usageCategory,
        String category,
        BigDecimal amount,
        String unit,
        String memo,
        String materialCostPerUnit,
        String laborCostPerUnit
) { }
