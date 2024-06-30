package com.interior.adapter.inbound.business.webdto;

import java.math.BigDecimal;

public class CreateBusinessMaterialWebDtoV1 {

    public record CreateBusinessMaterialReqDto(
            String materialName,
            String materialUsageCategory,
            String materialCategory,
            BigDecimal materialAmount,
            String materialAmountUnit,
            String materialMemo,
            String materialCostPerUnit,
            String laborCostPerUnit
    ) {

    }
}
