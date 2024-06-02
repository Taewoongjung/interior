package com.interior.adapter.inbound.user.webdto;

import com.interior.domain.company.Company;
import java.util.Set;

public class LoadUserDto {

    public record LoadUserResDto(
            String email,
            String tel,
            String name,
            String role,
            Set<Company> companyList
    ) {

    }
}
