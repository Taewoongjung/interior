package com.interior.adapter.inbound.business.webdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class GetBusinessMaterialLogsWebDtoV1 {

    public record Res(
            String updaterName,
            String changeField,
            String changeDetail,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
            LocalDateTime createdAt
    ) {

    }
}
