package com.interior.domain.company.repository.dto;

public record CreateCompany(
        String companyName,
        String mainAddress,
        String subAddress,
        String bdgNumber,
        long userId
) { }
