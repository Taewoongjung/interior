package com.interior.adapter.outbound.excel;

import com.querydsl.core.annotations.QueryProjection;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class BusinessMaterialExcelDownload {

    @ExcelColumnName(name = "카테고리")
    private String category;

    @ExcelColumnName(name = "재료명")
    private String materialName;

    @ExcelColumnName(name = "수량")
    private BigDecimal amount;

    @ExcelColumnName(name = "단위")
    private String unit;

    @ExcelColumnName(name = "재료비 - 단가")
    private String materialCostPerUnit;

//    @ExcelColumnName(name = "재료비 - 금액")
//    private String allMaterialCostPerUnit;

    @ExcelColumnName(name = "노무비 - 단가")
    private String laborCostPerUnit;

//    @ExcelColumnName(name = "노무비 - 금액")
//    private String allLaborCostPerUnit;
//
//    @ExcelColumnName(name = "합계 - 단가")
//    private String totalUnitPrice;
//
//    @ExcelColumnName(name = "합계 - 금액")
//    private String totalPrice;

    @QueryProjection
    public BusinessMaterialExcelDownload(
            final String category,
            final String materialName,
            final BigDecimal amount,
            final String unit,
            final String materialCostPerUnit,
//            final String allMaterialCostPerUnit,
            final String laborCostPerUnit
//            final String allLaborCostPerUnit,
//            final String totalUnitPrice,
//            final String totalPrice
    ) {
        this.category = category;
        this.materialName = materialName;
        this.amount = amount;
        this.unit = unit;
        this.materialCostPerUnit = materialCostPerUnit;
//        this.allMaterialCostPerUnit = allMaterialCostPerUnit;
        this.laborCostPerUnit = laborCostPerUnit;
//        this.allLaborCostPerUnit = allLaborCostPerUnit;
//        this.totalUnitPrice = totalUnitPrice;
//        this.totalPrice = totalPrice;
    }
}
