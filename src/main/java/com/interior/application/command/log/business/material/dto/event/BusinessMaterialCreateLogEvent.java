package com.interior.application.command.log.business.material.dto.event;

public record BusinessMaterialCreateLogEvent(Long businessId, Long businessMaterialId,
                                             Long updaterId, String afterData) {

}
