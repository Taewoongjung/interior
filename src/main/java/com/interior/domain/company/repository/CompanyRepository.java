package com.interior.domain.company.repository;

import com.interior.domain.company.Company;

public interface CompanyRepository {

    Company findById(final Long companyId);

    boolean save(final String userEmail, final Company createCompany);
}
