package com.interior.adapter.inbound.business.webdto;

public class CreateBusiness {

    public record CreateBusinessReqDto(String businessName) { }

    public record CreateBusinessResDto(Boolean isSuccess, Long createdBusinessId) { }
}
