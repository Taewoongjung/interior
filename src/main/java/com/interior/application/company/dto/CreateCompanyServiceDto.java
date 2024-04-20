package com.interior.application.company.dto;

public class CreateCompanyServiceDto {

    public record CreateCompanyDto(
            String companyName,
            String zipCode,
            String mainAddress,
            String subAddress,
            String bdgNumber,
            String tel
    ) { }
}
