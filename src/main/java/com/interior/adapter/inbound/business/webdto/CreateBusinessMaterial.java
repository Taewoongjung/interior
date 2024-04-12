package com.interior.adapter.inbound.business.webdto;

public class CreateBusinessMaterial {

    public record CreateBusinessMaterialReqDto(
            String materialName,
            String materialCategory,
            int materialAmount,
            String materialMemo
    ) { }
}
