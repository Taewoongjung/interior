package com.interior.application.command.log.business.dto.event;

public record BusinessDeleteLogEvent(Long businessId, Long updaterId, String originalBusinessName) {

}
