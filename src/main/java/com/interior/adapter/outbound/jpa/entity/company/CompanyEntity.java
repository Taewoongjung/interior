package com.interior.adapter.outbound.jpa.entity.company;

import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.interior.adapter.outbound.jpa.entity.BaseEntity;
import com.interior.adapter.outbound.jpa.entity.business.BusinessEntity;
import com.interior.adapter.outbound.jpa.entity.user.UserEntity;
import com.interior.application.command.business.dto.ReviseBusinessServiceDto;
import com.interior.domain.company.Company;
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
@Table(name = "company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "zipcode", nullable = false, columnDefinition = "varchar")
    private String zipCode;

    @Column(name = "owner_id", nullable = false, columnDefinition = "bigint")
    private Long ownerId;

    private String address;

    private String subAddress;

    private String buildingNumber;

    private String tel;

    @Column(name = "is_deleted", columnDefinition = "varchar(1)")
    @Enumerated(value = EnumType.STRING)
    private BoolType isDeleted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BusinessEntity> businessEntityList = new ArrayList<>();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity user;

    public CompanyEntity(
            final Long id,
            final String name,
            final String zipCode,
            final Long ownerId,
            final String address,
            final String subAddress,
            final String buildingNumber,
            final String tel,
            final BoolType isDeleted
    ) {
        super(LocalDateTime.now(), LocalDateTime.now());

        this.id = id;
        this.name = name;
        this.zipCode = zipCode;
        this.ownerId = ownerId;
        this.address = address;
        this.subAddress = subAddress;
        this.buildingNumber = buildingNumber;
        this.tel = tel;
        this.isDeleted = isDeleted;
    }

    public static CompanyEntity of(
            final String name,
            final String zipCode,
            final Long ownerId,
            final String address,
            final String subAddress,
            final String buildingNumber,
            final String tel,
            final BoolType isDeleted
    ) {
        return new CompanyEntity(null, name, zipCode, ownerId, address, subAddress, buildingNumber,
                tel, isDeleted);
    }

    public Company toPojo() {
        return Company.of(
                getId(),
                getName(),
                getZipCode(),
                getOwnerId(),
                getAddress(),
                getSubAddress(),
                getBuildingNumber(),
                getTel(),
                getIsDeleted(),
                getLastModified(),
                getCreatedAt()
        );
    }

    public Company toPojoWithRelations() {
        return Company.of(
                getId(),
                getName(),
                getZipCode(),
                getOwnerId(),
                getAddress(),
                getSubAddress(),
                getBuildingNumber(),
                getTel(),
                getIsDeleted(),
                getLastModified(),
                getCreatedAt(),
                getBusinessEntityList().stream().map(BusinessEntity::toPojoWithRelations)
                        .collect(Collectors.toList())
        );
    }

    public void deleteBusiness(final Long businessId) {
        this.businessEntityList.stream()
                .filter(f -> businessId.equals(f.getId())).findFirst()
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_BUSINESS.getMessage()));

        this.businessEntityList.removeIf(f -> businessId.equals(f.getId()));
    }

    public void reviseBusiness(final Long businessId, final ReviseBusinessServiceDto.Req req) {

        BusinessEntity business = this.businessEntityList.stream()
                .filter(f -> businessId.equals(f.getId())).findFirst()
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_BUSINESS.getMessage()));

        business.setName(req.changeBusinessName());
    }
}
