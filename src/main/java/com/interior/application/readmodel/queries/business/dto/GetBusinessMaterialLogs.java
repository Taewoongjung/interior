package com.interior.application.readmodel.queries.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class GetBusinessMaterialLogs {

    public record Res(
            String updaterName,
            String changeField,
            String changeDetail,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
            LocalDateTime createdAt
    ) {

    }
}
