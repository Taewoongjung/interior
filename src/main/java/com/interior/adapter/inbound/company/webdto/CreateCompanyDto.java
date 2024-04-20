package com.interior.adapter.inbound.company.webdto;

public class CreateCompanyDto {

    public record CreateCompanyReqDto(
            String companyName,
            String zipCode,
            String mainAddress,
            String subAddress,
            String bdgNumber,
            String tel
    ) { }
}
