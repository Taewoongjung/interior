package com.interior.application.command.log.business.material.dto.event;

public record BusinessMaterialDeleteLogEvent(Long businessId, Long materialId, Long updaterId) {

}
