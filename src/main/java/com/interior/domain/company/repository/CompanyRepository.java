package com.interior.domain.company.repository;

import com.interior.domain.company.Company;

public interface CompanyRepository {

    Company findByName(final String companyName);

    boolean save(final String userEmail, final Company createCompany);
}
