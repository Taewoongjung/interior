package com.interior.application.company;

import static com.interior.adapter.common.exception.ErrorType.LIMIT_OF_COMPANY_COUNT_IS_FIVE;
import static com.interior.util.CheckUtil.check;

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

    public Company getCompany(final long companyId) {
        return companyRepository.findById(companyId);
    }

    public boolean createCompany(
            User user,
            final CreateCompanyServiceDto.CreateCompanyDto reqDto
    ) {

        check(user.getCompanyList().size() >= 5, LIMIT_OF_COMPANY_COUNT_IS_FIVE);

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
