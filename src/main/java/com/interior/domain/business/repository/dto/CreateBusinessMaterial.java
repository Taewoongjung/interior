package com.interior.domain.business.repository.dto;

public record CreateBusinessMaterial(
        Long businessId,
        String name,
        String category,
        Integer amount,
        String memo
) { }
