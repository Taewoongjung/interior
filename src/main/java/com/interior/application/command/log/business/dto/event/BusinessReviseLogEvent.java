package com.interior.application.command.log.business.dto.event;

public record BusinessReviseLogEvent(Long businessId, Long updaterId, String originalBusinessName,
                                     String changeBusinessName) {

}
