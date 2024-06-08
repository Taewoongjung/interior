package com.interior.application.command.log.business.material.dto.event;

public record BusinessMaterialCreateEvent(Long businessId, Long businessMaterialId,
                                          Long updaterId, String afterData) {

}
