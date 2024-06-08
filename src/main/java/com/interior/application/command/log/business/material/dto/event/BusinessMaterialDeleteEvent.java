package com.interior.application.command.log.business.material.dto.event;

public record BusinessMaterialDeleteEvent(Long businessId, Long materialId, Long updaterId) {

}
