package com.interior.domain.business.expense;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessMaterialExpense {

    private Long id;
    private Long businessMaterialId;
    private String materialCostPerUnit;
    private String laborCostPerUnit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastModified;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private BusinessMaterialExpense(
            final Long id,
            final Long businessMaterialId,
            final String materialCostPerUnit,
            final String laborCostPerUnit,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.businessMaterialId = businessMaterialId;
        this.materialCostPerUnit = materialCostPerUnit;
        this.laborCostPerUnit = laborCostPerUnit;
        this.lastModified = lastModified;
        this.createdAt = createdAt;
    }

    public static BusinessMaterialExpense of(
            final Long id,
            final Long businessMaterialId,
            final String materialCostPerUnit,
            final String laborCostPerUnit,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {
        return new BusinessMaterialExpense(
                id,
                businessMaterialId,
                materialCostPerUnit,
                laborCostPerUnit,
                lastModified,
                createdAt);
    }

    public static BusinessMaterialExpense of(
            final Long businessMaterialId,
            final String materialCostPerUnit,
            final String laborCostPerUnit
    ) {
        return new BusinessMaterialExpense(
                null,
                businessMaterialId,
                materialCostPerUnit,
                laborCostPerUnit,
                LocalDateTime.now(), LocalDateTime.now());
    }
}
