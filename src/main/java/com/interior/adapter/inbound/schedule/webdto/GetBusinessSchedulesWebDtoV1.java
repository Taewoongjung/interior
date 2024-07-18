package com.interior.adapter.inbound.schedule.webdto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public class GetBusinessSchedulesWebDtoV1 {

    public record Req(
            @NotNull
            List<Long> businessIds) {

    }
}
