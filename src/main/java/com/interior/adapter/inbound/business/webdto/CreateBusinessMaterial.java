package com.interior.adapter.inbound.business.webdto;

import java.math.BigDecimal;
import lombok.ToString;

public class CreateBusinessMaterial {

    public record CreateBusinessMaterialReqDto(
        String materialName,
        String materialUsageCategory,
        String materialCategory,
        BigDecimal materialAmount,
        String materialAmountUnit,
        String materialMemo,
        String materialCostPerUnit,
        String laborCostPerUnit
    ) { }
}
