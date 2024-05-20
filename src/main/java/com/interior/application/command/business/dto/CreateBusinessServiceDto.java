package com.interior.application.command.business.dto;

import java.math.BigDecimal;

public class CreateBusinessServiceDto {

    public record CreateBusinessMaterialDto(
            String name,
            String usageCategory,
            String category,
            BigDecimal amount,
            String unit,
            String memo,
            String materialCostPerUnit,
            String laborCostPerUnit
    ) {

    }
}
