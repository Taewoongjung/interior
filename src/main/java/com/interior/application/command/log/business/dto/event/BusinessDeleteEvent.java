package com.interior.application.command.log.business.dto.event;

public record BusinessDeleteEvent(Long businessId, Long updaterId, String originalBusinessName) {

}
