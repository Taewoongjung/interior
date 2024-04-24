package com.interior.application.businesss.dto;

public class CreateBusinessServiceDto {

    public record CreateBusinessMaterialDto(
        String name,
        String usageCategory,
        String category,
        int amount,
        String unit,
        String memo,
        String materialCostPerUnit,
        String laborCostPerUnit
    ) { }
}
