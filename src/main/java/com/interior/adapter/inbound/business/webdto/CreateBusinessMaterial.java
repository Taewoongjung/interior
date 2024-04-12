package com.interior.adapter.inbound.business.webdto;

public class CreateBusinessMaterial {

    public record CreateBusinessMaterialReqDto(
            String name,
            String category,
            int amount,
            String memo
    ) { }
}
