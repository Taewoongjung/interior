package com.interior.adapter.outbound.jpa.entity.business;

import static com.interior.util.CheckUtil.check;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.interior.adapter.common.exception.ErrorType;
import com.interior.adapter.outbound.jpa.entity.BaseEntity;
import com.interior.adapter.outbound.jpa.entity.business.material.BusinessMaterialEntity;
import com.interior.adapter.outbound.jpa.entity.company.CompanyEntity;
import com.interior.domain.business.Business;
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
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Table(name = "business")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "company_id", nullable = false, columnDefinition = "bigint")
    private Long companyId;

    @Column(name = "customer_id", nullable = false, columnDefinition = "bigint")
    private Long customerId;

    private String status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BusinessMaterialEntity> businessMaterialList = new ArrayList<>();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "company_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CompanyEntity company;

    private BusinessEntity(
            final Long id,
            final String name,
            final Long companyId,
            final Long customerId,
            final String status,
            final List<BusinessMaterialEntity> businessMaterialList
    ) {
        super(LocalDateTime.now(), LocalDateTime.now());

        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.customerId = customerId;
        this.status = status;
        this.businessMaterialList = businessMaterialList;
    }

    public static BusinessEntity of(
            final String name,
            final Long companyId,
            final Long customerId,
            final String status
    ) {
        return new BusinessEntity(null, name, companyId, customerId, status, null);
    }

    public static BusinessEntity of(
            final String name,
            final Long companyId,
            final Long customerId,
            final String status,
            final List<BusinessMaterialEntity> businessMaterialList
    ) {
        return new BusinessEntity(null, name, companyId, customerId, status, businessMaterialList);
    }

    public Business toPojo() {
        return Business.of(
                getId(),
                getName(),
                getCompanyId(),
                getCustomerId(),
                getStatus(),
                getBusinessMaterialList().stream()
                        .map(BusinessMaterialEntity::toPojo)
                        .collect(Collectors.toList())
        );
    }

    public void setName(final String name) {
        check(name == null || "".equals(name.trim()), ErrorType.INVALID_BUSINESS_NAME);

        this.name = name;
    }

    public void deleteMaterial(final Long materialId) {
        check(materialId == null || materialId == 0, ErrorType.INAPPROPRIATE_REQUEST);

        this.businessMaterialList.stream()
                .filter(f -> materialId.equals(f.getId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        ErrorType.NOT_EXIST_BUSINESS_MATERIAL.getMessage()));

        this.businessMaterialList.removeIf(e -> materialId.equals(e.getId()));
    }
}
