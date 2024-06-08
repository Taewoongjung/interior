package com.interior.application.command.log.business.dto.event;

public record BusinessReviseEvent(Long businessId, Long updaterId, String originalBusinessName,
                                  String changeBusinessName) {

}
