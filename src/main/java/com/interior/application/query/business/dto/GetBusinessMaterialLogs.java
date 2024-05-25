package com.interior.application.query.business.dto;

import java.time.LocalDateTime;

public class GetBusinessMaterialLogs {

    public record Res(
            String updaterName,
            String changeField,
            String changeDetail,
            LocalDateTime createdAt
    ) {

    }
}
