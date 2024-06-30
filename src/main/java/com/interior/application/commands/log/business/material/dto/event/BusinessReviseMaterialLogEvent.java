package com.interior.application.commands.log.business.material.dto.event;

import com.interior.domain.business.material.log.BusinessMaterialChangeFieldType;

public record BusinessReviseMaterialLogEvent(
        Long businessId,
        Long businessMaterialId,
        Long updaterId,
        BusinessMaterialChangeFieldType type,
        String beforeData,
        String afterData
) {

    @Override
    public String beforeData() {
        if (beforeData == null) {
            return "없음";
        }

        return beforeData;
    }

    @Override
    public String afterData() {
        if (afterData == null) {
            return "없음";
        }

        return afterData;
    }

}
