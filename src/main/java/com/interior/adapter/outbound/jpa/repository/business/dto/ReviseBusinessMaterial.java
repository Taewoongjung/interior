package com.interior.adapter.outbound.jpa.repository.business.dto;

import com.interior.adapter.inbound.business.webdto.ReviseBusinessMaterialWebDtoV1;
import com.interior.domain.business.material.BusinessMaterial;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class ReviseBusinessMaterial {

    private final String materialName;
    private final String materialCategory;
    private final BigDecimal materialAmount;
    private final String materialAmountUnit;
    private final String materialMemo;
    private final String materialCostPerUnit;
    private final String laborCostPerUnit;

    private ReviseBusinessMaterial(
            final String materialName,
            final String materialCategory,
            final BigDecimal materialAmount,
            final String materialAmountUnit,
            final String materialMemo,
            final String materialCostPerUnit,
            final String laborCostPerUnit
    ) {

        this.materialName = materialName;
        this.materialCategory = materialCategory;
        this.materialAmount = materialAmount;
        this.materialAmountUnit = materialAmountUnit;
        this.materialMemo = materialMemo;
        this.materialCostPerUnit = materialCostPerUnit;
        this.laborCostPerUnit = laborCostPerUnit;
    }

    public static ReviseBusinessMaterial of(
            final ReviseBusinessMaterialWebDtoV1.Req webReq,
            final BusinessMaterial businessMaterial
    ) {

        String materialName = null;
        String materialCategory = null;
        BigDecimal materialAmount = null;
        String materialAmountUnit = null;
        String materialMemo = null;
        String materialCostPerUnit = null;
        String laborCostPerUnit = null;

        if (!businessMaterial.getName().equals(webReq.materialName())) {
            materialName = webReq.materialName();
        }

        if (!businessMaterial.getCategory().equals(webReq.materialCategory())) {
            materialCategory = webReq.materialCategory();
        }

        if (!(businessMaterial.getAmount().setScale(0)).equals(webReq.materialAmount())) {
            materialAmount = webReq.materialAmount();
        }

        if (!businessMaterial.getUnit().equals(webReq.materialAmountUnit())) {
            materialAmountUnit = webReq.materialAmountUnit();
        }

        if (!businessMaterial.getMemo().equals(webReq.materialMemo())) {
            materialMemo = webReq.materialMemo();
        }

        if (businessMaterial.getBusinessMaterialExpense() != null) {

            if (businessMaterial.getBusinessMaterialExpense().getMaterialCostPerUnit() != null &&
                    !businessMaterial.getBusinessMaterialExpense().getMaterialCostPerUnit()
                            .equals(webReq.materialCostPerUnit())) {
                materialCostPerUnit = webReq.materialCostPerUnit();
            }

            if (businessMaterial.getBusinessMaterialExpense().getLaborCostPerUnit() != null &&
                    !businessMaterial.getBusinessMaterialExpense().getLaborCostPerUnit()
                            .equals(webReq.laborCostPerUnit())) {
                laborCostPerUnit = webReq.laborCostPerUnit();
            }
        } else {
            materialCostPerUnit = webReq.materialCostPerUnit();
            laborCostPerUnit = webReq.laborCostPerUnit();
        }

        return new ReviseBusinessMaterial(
                materialName,
                materialCategory,
                materialAmount,
                materialAmountUnit,
                materialMemo,
                materialCostPerUnit,
                laborCostPerUnit
        );
    }
}
