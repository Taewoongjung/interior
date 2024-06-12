package com.interior.adapter.inbound.business.webdto;

public class CreateBusinessWebDtoV1 {

    public record Req(
            String businessName,
            String zipCode,
            String mainAddress,
            String subAddress,
            String bdgNumber
    ) {

    }

    public record Res(Boolean isSuccess, Long createdBusinessId) {

    }
}
