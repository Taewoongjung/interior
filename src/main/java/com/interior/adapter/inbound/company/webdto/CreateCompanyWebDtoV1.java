package com.interior.adapter.inbound.company.webdto;

public class CreateCompanyWebDtoV1 {

    public record Req(
            String companyName,
            String zipCode,
            String mainAddress,
            String subAddress,
            String bdgNumber,
            String tel
    ) {

    }
}
