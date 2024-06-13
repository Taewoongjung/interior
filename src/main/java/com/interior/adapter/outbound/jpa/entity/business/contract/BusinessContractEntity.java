package com.interior.adapter.outbound.jpa.entity.business.contract;

import com.interior.domain.business.contract.ContractType;
import com.interior.domain.util.BoolType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@ToString
@Table(name = "business_contract")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessContractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id", nullable = false, columnDefinition = "bigint")
    private Long companyId;

    @Column(name = "business_id", nullable = false, columnDefinition = "bigint")
    private Long businessId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "contract_type", nullable = false, columnDefinition = "varchar(50)")
    private ContractType contractType;

    @Column(name = "is_agreed", columnDefinition = "varchar(1)")
    @Enumerated(value = EnumType.STRING)
    private BoolType isAgreed;

    @Column(name = "user_id", nullable = false, columnDefinition = "bigint")
    private Long userId;

    @Column(name = "is_deleted", columnDefinition = "varchar(1)")
    @Enumerated(value = EnumType.STRING)
    private BoolType isDeleted;

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    private BusinessContractEntity(
            final Long id,
            final Long companyId,
            final Long businessId,
            final ContractType contractType,
            final BoolType isAgreed,
            final Long userId,
            final BoolType isDeleted
    ) {

        this.id = id;
        this.companyId = companyId;
        this.businessId = businessId;
        this.contractType = contractType;
        this.isAgreed = isAgreed;
        this.userId = userId;
        this.isDeleted = isDeleted;
        this.createdAt = LocalDateTime.now();
    }

    // 생성
    public static BusinessContractEntity of(
            final Long companyId,
            final Long businessId,
            final ContractType contractType,
            final BoolType isAgreed,
            final Long userId,
            final BoolType isDeleted
    ) {
        return new BusinessContractEntity(null, companyId, businessId, contractType, isAgreed,
                userId, isDeleted);
    }

    // 조회
    public static BusinessContractEntity of(
            final Long id,
            final Long companyId,
            final Long businessId,
            final ContractType contractType,
            final BoolType isAgreed,
            final Long userId,
            final BoolType isDeleted
    ) {
        return new BusinessContractEntity(id, companyId, businessId, contractType, isAgreed,
                userId, isDeleted);
    }
}
