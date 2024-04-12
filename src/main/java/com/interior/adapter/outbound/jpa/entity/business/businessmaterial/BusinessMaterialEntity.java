package com.interior.adapter.outbound.jpa.entity.business.businessmaterial;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.interior.adapter.outbound.jpa.entity.BaseEntity;
import com.interior.adapter.outbound.jpa.entity.business.BusinessEntity;
import com.interior.domain.business.businessmaterial.BusinessMaterial;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    private String category;

    private Integer amount;

    private String memo;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "business_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BusinessEntity business;

    private BusinessMaterialEntity(
            final Long id,
            final Long businessId,
            final String name,
            final String category,
            final Integer amount,
            final String memo
    ) {
        super(LocalDateTime.now(), LocalDateTime.now());

        this.id = id;
        this.businessId = businessId;
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.memo = memo;
    }

    public static BusinessMaterialEntity of(
            final Long businessId,
            final String name,
            final String category,
            final Integer amount,
            final String memo
    ) {
        return new BusinessMaterialEntity(null, businessId, name, category, amount, memo);
    }

    public BusinessMaterial toPojo() {
        return BusinessMaterial.of(
                getId(),
                getBusinessId(),
                getName(),
                getCategory(),
                getAmount(),
                getMemo()
        );
    }
}