package com.interior.adapter.inbound.company.webdto;

import com.interior.domain.company.Company;
import com.interior.domain.user.User;

public class GetCompanyDto {

    public record GetCompanyResDto(User user, Company company) { }
}
