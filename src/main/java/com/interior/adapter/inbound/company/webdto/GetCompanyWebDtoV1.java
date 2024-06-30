package com.interior.adapter.inbound.company.webdto;

import com.interior.domain.company.Company;

public class GetCompanyWebDtoV1 {

    public record Res(
            String userName,
            Company company
    ) {

    }
}
