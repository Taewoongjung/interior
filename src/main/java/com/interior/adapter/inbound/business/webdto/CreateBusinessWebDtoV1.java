package com.interior.adapter.inbound.business.webdto;

public class CreateBusinessWebDtoV1 {

    public record Req(String businessName) {
    }

    public record Res(Boolean isSuccess, Long createdBusinessId) {
    }
}
