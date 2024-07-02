package com.interior.adapter.outbound.jpa.repository.company;

import static com.interior.adapter.common.exception.ErrorType.COMPANY_NOT_EXIST_IN_THE_USER;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_COMPANY;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_CUSTOMER;
import static com.interior.util.CheckUtil.check;
import static com.interior.util.converter.jpa.company.CompanyEntityConverter.companyToEntity;

import com.interior.adapter.outbound.jpa.entity.company.CompanyEntity;
import com.interior.adapter.outbound.jpa.entity.user.UserEntity;
import com.interior.adapter.outbound.jpa.repository.user.UserJpaRepository;
import com.interior.domain.company.Company;
import com.interior.domain.company.repository.CompanyRepository;
import com.interior.domain.util.BoolType;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryAdapter implements CompanyRepository {

    private final UserJpaRepository userJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public Company findById(final Long companyId) {

        CompanyEntity company = companyJpaRepository.findById(companyId)
                .orElseThrow(() -> new NoSuchElementException(
                        NOT_EXIST_COMPANY.getMessage()));

        check(BoolType.T == company.getIsDeleted(), NOT_EXIST_COMPANY);

        return company.toPojoWithRelations();
    }

    @Override
    @Transactional
    public boolean save(final String userEmail, final Company addingCompany) {
        UserEntity user = userJpaRepository.findByEmail(userEmail);
        check(user == null, NOT_EXIST_CUSTOMER);

        user.getCompanyEntityList().add(companyToEntity(addingCompany));

        userJpaRepository.save(user);

        return true;
    }

    @Override
    @Transactional
    public boolean delete(final Long userId, final Long companyId) {

        UserEntity user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(
                        NOT_EXIST_CUSTOMER.getMessage()));

        check(user.getCompanyEntityList().stream().noneMatch(f -> companyId.equals(f.getId())),
                COMPANY_NOT_EXIST_IN_THE_USER);

        user.getCompanyEntityList().stream().filter(f -> companyId.equals(f.getId()))
                .forEach(CompanyEntity::delete);

        return true;
    }
}
