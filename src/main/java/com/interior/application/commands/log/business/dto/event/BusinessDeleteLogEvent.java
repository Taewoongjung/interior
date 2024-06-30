package com.interior.application.commands.log.business.dto.event;

public record BusinessDeleteLogEvent(Long businessId, Long updaterId, String originalBusinessName) {

}
