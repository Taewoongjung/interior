package com.interior.domain.business.repository.dto;

public record CreateBusiness(
        String businessName,
        Long companyId,
        Long customerId,
        String zipCode,
        String mainAddress,
        String subAddress,
        String bdgNumber
) {

}
