package com.interior.application.company;

import static com.interior.adapter.common.exception.ErrorType.LIMIT_OF_COMPANY_COUNT_IS_FIVE;
import static com.interior.util.CheckUtil.check;

import com.interior.application.company.dto.CreateCompanyServiceDto;
import com.interior.domain.company.Company;
import com.interior.domain.company.repository.CompanyRepository;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Transactional(readOnly = true)
    public Company getCompany(final String userEmail, final Long companyId) {

        User user = userRepository.findByEmail(userEmail);

        return user.getCompanyList().stream()
                .filter(f -> companyId.equals(f.getId()))
                .findFirst().orElse(null);
    }

    @Transactional
    public boolean createCompany(
            User user,
            final CreateCompanyServiceDto.CreateCompanyDto reqDto
    ) {

        check(user.getCompanyList().size() >= 5, LIMIT_OF_COMPANY_COUNT_IS_FIVE);

        Company company = Company.of(
                reqDto.companyName(),
                reqDto.zipCode(),
                user.getId(),
                reqDto.mainAddress(),
                reqDto.subAddress(),
                reqDto.bdgNumber(),
                reqDto.tel(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        if (companyRepository.save(user.getEmail(), company)) {
            return true;
        }

        return false;
    }

    @Transactional
    public boolean deleteCompany(final Long userId, final Long companyId) {

        companyRepository.delete(userId, companyId);

        return true;
    }
}
