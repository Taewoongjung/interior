package com.interior.adapter.outbound.jpa.repository.company;

import static com.interior.util.converter.jpa.company.CompanyEntityConverter.companyToEntity;

import com.interior.adapter.common.exception.ErrorType;
import com.interior.adapter.outbound.jpa.entity.company.CompanyEntity;
import com.interior.adapter.outbound.jpa.entity.user.UserEntity;
import com.interior.adapter.outbound.jpa.repository.user.UserJpaRepository;
import com.interior.domain.company.Company;
import com.interior.domain.company.repository.CompanyRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryAdapter implements CompanyRepository {

    private final UserJpaRepository userJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;

    @Override
    public Company findById(final Long companyId) {

        CompanyEntity company = companyJpaRepository.findById(companyId)
                .orElseThrow(() -> new NoSuchElementException(
                        ErrorType.NOT_EXIST_COMPANY.getMessage()));

        return company.toPojo();
    }

    @Override
    public boolean save(final String userEmail, final Company addingCompany) {
        UserEntity user = userJpaRepository.findByEmail(userEmail);

        user.getCompanyEntityList().add(companyToEntity(addingCompany));

        userJpaRepository.save(user);

        return true;
    }
}
