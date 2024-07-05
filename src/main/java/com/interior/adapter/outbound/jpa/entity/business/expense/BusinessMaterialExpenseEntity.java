package com.interior.adapter.outbound.jpa.entity.business.expense;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_MATERIAL_ID;
import static com.interior.util.CheckUtil.require;

import com.interior.adapter.outbound.jpa.entity.BaseEntity;
import com.interior.adapter.outbound.jpa.entity.business.material.BusinessMaterialEntity;
import com.interior.domain.business.expense.BusinessMaterialExpense;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Table(name = "business_material_expense")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessMaterialExpenseEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_material_id", nullable = false, columnDefinition = "bigint")
    private Long businessMaterialId;

    @Column(name = "material_cost_per_unit", columnDefinition = "varchar")
    private String materialCostPerUnit;

    @Column(name = "labor_cost_per_unit", columnDefinition = "varchar")
    private String laborCostPerUnit;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "business_material_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BusinessMaterialEntity businessMaterial;

    private BusinessMaterialExpenseEntity(
            final Long id,
            final Long businessMaterialId,
            final String materialCostPerUnit,
            final String laborCostPerUnit
    ) {
        super(LocalDateTime.now(), LocalDateTime.now());

        this.id = id;
        this.businessMaterialId = businessMaterialId;
        this.materialCostPerUnit = materialCostPerUnit;
        this.laborCostPerUnit = laborCostPerUnit;
    }

    public static BusinessMaterialExpenseEntity of(
            final Long businessMaterialId,
            final String materialCostPerUnit,
            final String laborCostPerUnit
    ) {
        require(o -> businessMaterialId == null, businessMaterialId, EMPTY_BUSINESS_MATERIAL_ID);

        return new BusinessMaterialExpenseEntity(null, businessMaterialId, materialCostPerUnit,
                laborCostPerUnit);
    }

    public BusinessMaterialExpense toPojo() {
        return BusinessMaterialExpense.of(
                getId(),
                getBusinessMaterialId(),
                getMaterialCostPerUnit(),
                getLaborCostPerUnit(),
                getLastModified(),
                getCreatedAt()
        );
    }

    public void setMaterialCostPerUnit(final String materialCostPerUnit) {
        this.materialCostPerUnit = materialCostPerUnit;
    }

    public void setLaborCostPerUnit(final String laborCostPerUnit) {
        this.laborCostPerUnit = laborCostPerUnit;
    }
}
