package com.interior.domain.business.repository.dto;

public record CreateBusiness(
    String businessName,
    Long hostId,
    Long customerId,
    String status
) { }
