package com.interior.adapter.inbound.business.webdto;

import lombok.ToString;

public class CreateBusinessMaterial {

    public record CreateBusinessMaterialReqDto(
        String materialName,
        String materialUsageCategory,
        String materialCategory,
        int materialAmount,
        String materialAmountUnit,
        String materialMemo,
        String materialCostPerUnit,
        String laborCostPerUnit
    ) { }
}
