package com.interior.domain.business.material;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_MATERIAL_AMOUNT;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_MATERIAL_CATEGORY;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_MATERIAL_NAME;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_RELATED_BUSINESS_TO_BUSINESS_MATERIAL;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_USAGE_CATEGORY_INVALID;
import static com.interior.util.CheckUtil.require;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.interior.domain.business.expense.BusinessMaterialExpense;
import com.interior.domain.util.BoolType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessMaterial {

    private Long id;

    private Long businessId;

    private String name;

    private String usageCategory;

    private String category;

    private BigDecimal amount;

    private String unit;

    private String memo;

    private BoolType isDeleted;

    private BusinessMaterialExpense businessMaterialExpense;

    private String allMaterialCostPerUnit;

    private String allLaborCostPerUnit;

    private String totalUnitPrice;

    private String totalPrice;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastModified;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;


    private BusinessMaterial(
            final Long id,
            final Long businessId,
            final String name,
            final String usageCategory,
            final String category,
            final BigDecimal amount,
            final String unit,
            final String memo,
            final BoolType isDeleted,
            final BusinessMaterialExpense businessMaterialExpense,
            final String allMaterialCostPerUnit,
            final String allLaborCostPerUnit,
            final String totalUnitPrice,
            final String totalPrice,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.businessId = businessId;
        this.name = name;
        this.usageCategory = usageCategory;
        this.category = category;
        this.amount = amount;
        this.unit = unit;
        this.memo = memo;
        this.isDeleted = isDeleted;
        this.businessMaterialExpense = businessMaterialExpense;
        this.allMaterialCostPerUnit = allMaterialCostPerUnit;
        this.allLaborCostPerUnit = allLaborCostPerUnit;
        this.totalUnitPrice = totalUnitPrice;
        this.totalPrice = totalPrice;
        this.lastModified = lastModified;
        this.createdAt = createdAt;
    }

    // 조회 전용
    public static BusinessMaterial of(
            final Long id,
            final Long businessId,
            final String name,
            final String usageCategory,
            final String category,
            final BigDecimal amount,
            final String unit,
            final String memo,
            final BusinessMaterialExpense businessMaterialExpense
    ) {

        require(o -> businessId == null, businessId, EMPTY_RELATED_BUSINESS_TO_BUSINESS_MATERIAL);
        require(o -> name == null, name, EMPTY_BUSINESS_MATERIAL_NAME);
        require(o -> (usageCategory == null || "".equals(usageCategory.trim())),
                usageCategory, EMPTY_USAGE_CATEGORY_INVALID);
        require(o -> category == null, category, EMPTY_BUSINESS_MATERIAL_CATEGORY);
        require(o -> amount == null, amount, EMPTY_BUSINESS_MATERIAL_AMOUNT);

        String allMaterialCostPerUnit = null;
        String allLaborCostPerUnit = null;
        String totalUnitPrice = null;
        String totalPrice = null;

        if (businessMaterialExpense != null) {

            BigDecimal materialCostPerUnit = new BigDecimal(
                    businessMaterialExpense.getMaterialCostPerUnit());

            // 총 재료비 단가
            allMaterialCostPerUnit = setAllAmountOfSpecificExpense(amount,
                    materialCostPerUnit).toPlainString();

            BigDecimal laborCostPerUnit = new BigDecimal(
                    businessMaterialExpense.getLaborCostPerUnit());

            // 총 노무비 단가
            allLaborCostPerUnit = setAllAmountOfSpecificExpense(amount,
                    laborCostPerUnit).toPlainString();

            // 총 단가
            BigDecimal totalUnitPriceBigDecimal = materialCostPerUnit.add(laborCostPerUnit);
            totalUnitPrice = removeDecimal(totalUnitPriceBigDecimal).toPlainString();

            // 총 금액
            totalPrice = removeDecimal(totalUnitPriceBigDecimal.multiply(amount)).toPlainString();
        }

        return new BusinessMaterial(
                id,
                businessId,
                name,
                usageCategory,
                category,
                amount,
                unit,
                memo,
                null,
                businessMaterialExpense,
                allMaterialCostPerUnit, allLaborCostPerUnit,
                totalUnitPrice, totalPrice,
                LocalDateTime.now(), LocalDateTime.now());
    }

    private static BigDecimal setAllAmountOfSpecificExpense(final BigDecimal amount,
            final BigDecimal targetCostPerUnit) {

        BigDecimal totalTargetCostPerUnit = amount.multiply(targetCostPerUnit);

        return removeDecimal(totalTargetCostPerUnit);
    }

    private static BigDecimal removeDecimal(final BigDecimal target) {

        BigDecimal response = null;

        // 결과가 소수점 이하 모두 0인지 확인
        if (target.scale() <= 0 || target.stripTrailingZeros().scale() <= 0) {
            // 소수점 이하 모두 0이면 소수점 제거
            response = target.setScale(0, RoundingMode.UNNECESSARY);
        }
        return response;
    }

    // 생성 전용
    public static BusinessMaterial of(
            final Long businessId,
            final String name,
            final String usageCategory,
            final String category,
            final BigDecimal amount,
            final String unit,
            final String memo
    ) {

        require(o -> businessId == null, businessId, EMPTY_RELATED_BUSINESS_TO_BUSINESS_MATERIAL);
        require(o -> name == null, name, EMPTY_BUSINESS_MATERIAL_NAME);
        require(o -> (usageCategory == null || "".equals(usageCategory.trim())),
                usageCategory, EMPTY_USAGE_CATEGORY_INVALID);
        require(o -> category == null, category, EMPTY_BUSINESS_MATERIAL_CATEGORY);
        require(o -> amount == null, amount, EMPTY_BUSINESS_MATERIAL_AMOUNT);

        return new BusinessMaterial(
                null,
                businessId,
                name,
                usageCategory,
                category,
                amount,
                unit,
                memo,
                BoolType.F,
                null,
                null, null,
                null, null,
                LocalDateTime.now(), LocalDateTime.now());
    }
}
