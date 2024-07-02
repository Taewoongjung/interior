package com.interior.domain.business.contract;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_COMPANY_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_CONTRACT_TYPE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_CONTRACT_USER_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_AGREED;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_DELETED;
import static com.interior.util.CheckUtil.require;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessContract {

    private final Long id;

    private final Long companyId;

    private final Long businessId;

    private final ContractType contractType;

    private final BoolType isAgreed;

    private final Long userId;

    private final BoolType isDeleted;

    private final LocalDateTime createdAt;

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
        this.createdAt = createdAt;
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

        require(o -> companyId == null, businessId, EMPTY_COMPANY_ID);
        require(o -> businessId == null, businessId, EMPTY_BUSINESS_ID);
        require(o -> contractType == null, contractType, EMPTY_CONTRACT_TYPE);
        require(o -> isAgreed == null, isAgreed, EMPTY_IS_AGREED);
        require(o -> userId == null, userId, EMPTY_CONTRACT_USER_ID);
        require(o -> isDeleted == null, isDeleted, EMPTY_IS_DELETED);

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

        require(o -> companyId == null, businessId, EMPTY_COMPANY_ID);
        require(o -> businessId == null, businessId, EMPTY_BUSINESS_ID);
        require(o -> contractType == null, contractType, EMPTY_CONTRACT_TYPE);
        require(o -> isAgreed == null, isAgreed, EMPTY_IS_AGREED);
        require(o -> userId == null, userId, EMPTY_CONTRACT_USER_ID);
        require(o -> isDeleted == null, isDeleted, EMPTY_IS_DELETED);

        return new BusinessContract(id, companyId, businessId, contractType, isAgreed,
                userId, isDeleted, createdAt);
    }
}
