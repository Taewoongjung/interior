package com.interior.adapter.outbound.jpa.entity.business.material;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_USAGE_CATEGORY_INVALID;
import static com.interior.util.CheckUtil.check;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.interior.adapter.outbound.jpa.entity.business.BusinessEntity;
import com.interior.adapter.outbound.jpa.entity.BaseEntity;
import com.interior.adapter.outbound.jpa.entity.business.expense.BusinessMaterialExpenseEntity;
import com.interior.domain.business.material.BusinessMaterial;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Table(name = "business_material")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessMaterialEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_id", nullable = false, columnDefinition = "bigint")
    private Long businessId;

    private String name;

    private String usageCategory;

    private String category;

    private Integer amount;

    private String unit;

    private String memo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "businessMaterial", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BusinessMaterialExpenseEntity> businessMaterialExpenseEntityList = new ArrayList<>();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "business_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BusinessEntity business;

    private BusinessMaterialEntity(
            final Long id,
            final Long businessId,
            final String name,
            final String usageCategory,
            final String category,
            final Integer amount,
            final String unit,
            final String memo
    ) {
        super(LocalDateTime.now(), LocalDateTime.now());

        this.id = id;
        this.businessId = businessId;
        this.name = name;
        this.usageCategory = usageCategory;
        this.category = category;
        this.amount = amount;
        this.unit = unit;
        this.memo = memo;
    }

    public static BusinessMaterialEntity of(
            final Long businessId,
            final String name,
            final String usageCategory,
            final String category,
            final Integer amount,
            final String unit,
            final String memo
    ) {
        return new BusinessMaterialEntity(null, businessId, name, usageCategory, category, amount, unit, memo);
    }

    public BusinessMaterial toPojo() {
        return BusinessMaterial.of(
                getId(),
                getBusinessId(),
                getName(),
                getUsageCategory(),
                getCategory(),
                getAmount(),
                getUnit(),
                getMemo()
        );
    }

    public void setUsageCategory(final String usageCategory) {
        check("".equals(usageCategory.trim()), EMPTY_USAGE_CATEGORY_INVALID);

        this.usageCategory = usageCategory;
    }
}
