package com.interior.adapter.inbound.business.webdto;

import java.math.BigDecimal;

public class ReviseBusinessMaterialWebDtoV1 {

    public record Req(
            String materialName,
            String materialCategory,
            BigDecimal materialAmount,
            String materialAmountUnit,
            String materialMemo,
            String materialCostPerUnit,
            String laborCostPerUnit
    ) {
    }
}
