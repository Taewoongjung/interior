package com.interior.adapter.outbound.jpa.entity.business;

import com.interior.adapter.outbound.jpa.entity.BaseEntity;
import com.interior.adapter.outbound.jpa.entity.business.businessmaterial.BusinessMaterialEntity;
import com.interior.domain.business.Business;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @Column(name = "host_id", nullable = false, columnDefinition = "bigint")
    private Long hostId;

    @Column(name = "customer_id", nullable = false, columnDefinition = "bigint")
    private Long customerId;

    private String status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BusinessMaterialEntity> businessMaterialList = new ArrayList<>();

    private BusinessEntity(
            final Long id,
            final String name,
            final Long hostId,
            final Long customerId,
            final String status,
            final List<BusinessMaterialEntity> businessMaterialList
    ) {
        super(LocalDateTime.now(), LocalDateTime.now());

        this.id = id;
        this.name = name;
        this.hostId = hostId;
        this.customerId = customerId;
        this.status = status;
        this.businessMaterialList = businessMaterialList;
    }

    public static BusinessEntity of(
            final String name,
            final Long hostId,
            final Long customerId,
            final String status
    ) {
        return new BusinessEntity(null, name, hostId, customerId, status, null);
    }

    public static BusinessEntity of(
            final String name,
            final Long hostId,
            final Long customerId,
            final String status,
            final List<BusinessMaterialEntity> businessMaterialList
    ) {
        return new BusinessEntity(null, name, hostId, customerId, status, businessMaterialList);
    }

    public Business toPojo() {
        return Business.of(
                getId(),
                getName(),
                getHostId(),
                getCustomerId(),
                getStatus(),
                getBusinessMaterialList().stream()
                        .map(BusinessMaterialEntity::toPojo)
                        .collect(Collectors.toList())
        );
    }
}
