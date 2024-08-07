package com.interior.adapter.outbound.jpa.entity.business.material;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_MATERIAL_AMOUNT;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_MATERIAL_CATEGORY;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_MATERIAL_NAME;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_UNIT;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_RELATED_BUSINESS_TO_BUSINESS_MATERIAL;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_USAGE_CATEGORY_INVALID;
import static com.interior.util.CheckUtil.check;
import static com.interior.util.CheckUtil.require;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.interior.adapter.outbound.jpa.entity.BaseEntity;
import com.interior.adapter.outbound.jpa.entity.business.BusinessEntity;
import com.interior.adapter.outbound.jpa.entity.business.expense.BusinessMaterialExpenseEntity;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.util.BoolType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    private BigDecimal amount;

    private String unit;

    private String memo;

    @Column(name = "is_deleted", columnDefinition = "varchar(1)")
    @Enumerated(value = EnumType.STRING)
    private BoolType isDeleted;

    @OneToOne(mappedBy = "businessMaterial", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private BusinessMaterialExpenseEntity businessMaterialExpense;

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
            final BigDecimal amount,
            final String unit,
            final String memo,
            final BoolType isDeleted,
            final BusinessMaterialExpenseEntity businessMaterialExpense
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
        this.isDeleted = isDeleted;
        this.businessMaterialExpense = businessMaterialExpense;
    }

    public static BusinessMaterialEntity of(
            final Long businessId,
            final String name,
            final String usageCategory,
            final String category,
            final BigDecimal amount,
            final String unit,
            final String memo,
            final BoolType isDeleted,
            final BusinessMaterialExpenseEntity businessMaterialExpense
    ) {

        require(o -> businessId == null, businessId, EMPTY_RELATED_BUSINESS_TO_BUSINESS_MATERIAL);
        require(o -> name == null, name, EMPTY_BUSINESS_MATERIAL_NAME);
        require(o -> (usageCategory == null || "".equals(usageCategory.trim())),
                usageCategory, EMPTY_USAGE_CATEGORY_INVALID);
        require(o -> category == null, category, EMPTY_BUSINESS_MATERIAL_CATEGORY);
        require(o -> amount == null, amount, EMPTY_BUSINESS_MATERIAL_AMOUNT);
        require(o -> unit == null, unit, EMPTY_BUSINESS_UNIT);

        return new BusinessMaterialEntity(null, businessId, name, usageCategory, category, amount,
                unit, memo, isDeleted, businessMaterialExpense);
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
                getMemo(),
                getIsDeleted(),
                getLastModified(),
                getCreatedAt(),
                businessMaterialExpense != null ?
                        businessMaterialExpense.toPojo() : null
        );
        /**
         getBusinessMaterialExpense() 삼항연산자를 이용한 이유는
         business를 조회를 할 때 businessMaterial 도 같이 조회 하는데 이 과정에서
         businessMaterialExpense 도 같이 조회되는데, 해당 객체가 필요 없을 때 null 일 수도
         있기 때문에 사용 함.
         * */
    }

    public void setUsageCategory(final String usageCategory) {
        check("".equals(usageCategory.trim()), EMPTY_USAGE_CATEGORY_INVALID);

        this.usageCategory = usageCategory;
    }

    public void setBusinessMaterialExpense(final BusinessMaterialExpenseEntity materialExpense) {
        this.businessMaterialExpense = materialExpense;
    }

    public void setDeleted(final BoolType deleted) {
        this.isDeleted = deleted;
    }

    public void setBusinessMaterialName(final String businessMaterialName) {
        this.name = businessMaterialName;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setUnit(final String unit) {
        this.unit = unit;
    }

    public void setMemo(final String memo) {
        this.memo = memo;
    }
}
