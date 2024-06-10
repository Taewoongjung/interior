package com.interior.util.converter.jpa.company;

import com.interior.adapter.outbound.jpa.entity.company.CompanyEntity;
import com.interior.domain.company.Company;

public class CompanyEntityConverter {

    public static CompanyEntity companyToEntity(final Company company) {
        return CompanyEntity.of(
                company.getName(),
                company.getZipCode(),
                company.getOwnerId(),
                company.getAddress(),
                company.getSubAddress(),
                company.getBuildingNumber(),
                company.getTel(),
                company.getIsDeleted()
        );
    }
}
