package com.interior.application.command.log.business.material.dto.event;

public record BusinessDeletedEvent(Long businessId, Long updaterId, String originalBusinessName) {

}
