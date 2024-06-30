package com.interior.application.commands.log.business.material.dto.event;

public record BusinessMaterialDeleteLogEvent(Long businessId, Long materialId, Long updaterId) {

}
