package com.interior.application.queries.company;

import com.interior.domain.company.Company;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import com.interior.domain.util.BoolType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyQueryService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Company getCompany(final String userEmail, final Long companyId) {

        User user = userRepository.findByEmail(userEmail);

        return user.getCompanyList().stream()
                .filter(f -> companyId.equals(f.getId()))
                .filter(f -> f.getIsDeleted() == BoolType.F)
                .findFirst().orElse(null);
    }
}
