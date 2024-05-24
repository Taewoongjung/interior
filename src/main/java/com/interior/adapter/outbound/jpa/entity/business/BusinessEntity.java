package com.interior.adapter.outbound.jpa.entity.business;

import static com.interior.adapter.common.exception.ErrorType.INVALID_BUSINESS_NAME;
import static com.interior.util.CheckUtil.check;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.interior.adapter.common.exception.ErrorType;
import com.interior.adapter.outbound.jpa.entity.BaseEntity;
import com.interior.adapter.outbound.jpa.entity.business.material.BusinessMaterialEntity;
import com.interior.adapter.outbound.jpa.entity.company.CompanyEntity;
import com.interior.domain.business.Business;
import com.interior.domain.business.BusinessStatus;
import com.interior.domain.business.BusinessStatusDetail;
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

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "varchar(20)")
    private BusinessStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "varchar(50)")
    private BusinessStatusDetail statusDetail;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
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
            final BusinessStatus status,
            final BusinessStatusDetail statusDetail,
            final List<BusinessMaterialEntity> businessMaterialList
    ) {
        super(LocalDateTime.now(), LocalDateTime.now());

        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.customerId = customerId;
        this.status = status;
        this.statusDetail = statusDetail;
        this.businessMaterialList = businessMaterialList;
    }

    public static BusinessEntity of(
            final String name,
            final Long companyId,
            final Long customerId,
            final BusinessStatus status
    ) {

        check(name == null || "".equals(name.trim()), INVALID_BUSINESS_NAME);
        check(name.toCharArray().length < 1, INVALID_BUSINESS_NAME); // 사업명은 2 글자 이상

        return new BusinessEntity(null, name, companyId, customerId, status, null, null);
    }

    public static BusinessEntity of(
            final String name,
            final Long companyId,
            final Long customerId,
            final BusinessStatus status,
            final BusinessStatusDetail statusDetail,
            final List<BusinessMaterialEntity> businessMaterialList
    ) {

        check(name == null || "".equals(name.trim()), INVALID_BUSINESS_NAME);
        check(name.toCharArray().length < 1, INVALID_BUSINESS_NAME);

        return new BusinessEntity(null, name, companyId, customerId, status, statusDetail,
                businessMaterialList);
    }

    public Business toPojo() {
        return Business.of(
                getId(),
                getName(),
                getCompanyId(),
                getCustomerId(),
                getStatus(),
                getStatusDetail()
        );
    }

    public Business toPojoWithRelations() {
        return Business.of(
                getId(),
                getName(),
                getCompanyId(),
                getCustomerId(),
                getStatus(),
                getStatusDetail(),
                getBusinessMaterialList().stream()
                        .map(BusinessMaterialEntity::toPojo)
                        .collect(Collectors.toList())
        );
    }

    public void getOnlyNotDeletedMaterials() {
        this.getBusinessMaterialList().removeIf(e -> BoolType.T.equals(e.getIsDeleted()));
    }

    public void setName(final String name) {
        check(name == null || "".equals(name.trim()), ErrorType.INVALID_BUSINESS_NAME);
        check(name.toCharArray().length < 1, INVALID_BUSINESS_NAME);

        this.name = name;
    }

    public void deleteMaterial(final Long materialId) {
        check(materialId == null || materialId == 0, ErrorType.INAPPROPRIATE_REQUEST);

        this.businessMaterialList.stream()
                .filter(f -> materialId.equals(f.getId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        ErrorType.NOT_EXIST_BUSINESS_MATERIAL.getMessage()));

        this.businessMaterialList.stream()
                .filter(f -> materialId.equals(f.getId()))
                .forEach(e -> e.setDeleted(BoolType.T));
    }


}
