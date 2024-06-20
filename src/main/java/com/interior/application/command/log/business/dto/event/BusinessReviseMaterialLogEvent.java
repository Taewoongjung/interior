package com.interior.application.command.log.business.dto.event;

import com.interior.domain.business.log.BusinessChangeFieldType;

public record BusinessReviseMaterialLogEvent(
        Long businessId,
        Long updaterId,
        BusinessChangeFieldType type,
        String originalBusinessName,
        String changeBusinessName
) {
}
