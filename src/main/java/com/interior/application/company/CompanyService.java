package com.interior.application.company;

import com.interior.application.company.dto.CreateCompanyServiceDto;
import com.interior.domain.company.Company;
import com.interior.domain.company.repository.CompanyRepository;
import com.interior.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<Company> getCompany(final User user) {
        return user.getCompanyList();
    }

    public boolean createCompany(
            User user,
            final CreateCompanyServiceDto.CreateCompanyDto reqDto
    ) {

        Company company = Company.of(
                reqDto.companyName(),
                user.getId(),
                reqDto.mainAddress(),
                reqDto.subAddress(),
                reqDto.bdgNumber(),
                reqDto.tel());

        if (companyRepository.save(user.getEmail(), company)) {
            return true;
        }

        return false;
    }
}
