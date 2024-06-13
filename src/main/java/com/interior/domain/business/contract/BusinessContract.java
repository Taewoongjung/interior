package com.interior.domain.business.contract;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.interior.adapter.outbound.jpa.entity.business.contract.ContractType;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessContract {

    private Long id;

    private Long companyId;

    private Long businessId;

    private ContractType contractType;

    private BoolType isAgreed;

    private Long userId;

    private BoolType isDeleted;

    private LocalDateTime createdAt;

    private BusinessContract(
            final Long id,
            final Long companyId,
            final Long businessId,
            final ContractType contractType,
            final BoolType isAgreed,
            final Long userId,
            final BoolType isDeleted,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.companyId = companyId;
        this.businessId = businessId;
        this.contractType = contractType;
        this.isAgreed = isAgreed;
        this.userId = userId;
        this.isDeleted = isDeleted;
    }

    // 생성
    public static BusinessContract of(
            final Long companyId,
            final Long businessId,
            final ContractType contractType,
            final BoolType isAgreed,
            final Long userId,
            final BoolType isDeleted
    ) {
        return new BusinessContract(null, companyId, businessId, contractType, isAgreed,
                userId, isDeleted, null);
    }

    // 조회
    public static BusinessContract of(
            final Long id,
            final Long companyId,
            final Long businessId,
            final ContractType contractType,
            final BoolType isAgreed,
            final Long userId,
            final BoolType isDeleted,
            final LocalDateTime createdAt
    ) {
        return new BusinessContract(id, companyId, businessId, contractType, isAgreed,
                userId, isDeleted, createdAt);
    }
}
