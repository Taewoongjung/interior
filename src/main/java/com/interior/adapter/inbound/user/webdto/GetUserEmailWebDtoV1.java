package com.interior.adapter.inbound.user.webdto;

import java.time.LocalDateTime;

public class GetUserEmailWebDtoV1 {

    public record Res(String email, LocalDateTime joinedDate) {

    }
}
