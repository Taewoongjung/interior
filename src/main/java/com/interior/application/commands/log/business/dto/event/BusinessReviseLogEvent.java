package com.interior.application.commands.log.business.dto.event;

import com.interior.domain.business.log.BusinessChangeFieldType;

public record BusinessReviseLogEvent(
        Long businessId,
        Long updaterId,
        BusinessChangeFieldType type,
        String originalBusinessName,
        String changeBusinessName
) {

}
