package com.interior.application.businesss.dto;

public class CreateBusinessServiceDto {

    public record CreateBusinessMaterialDto(
        String name,
        String category,
        int amount,
        String memo
    ) { }
}
