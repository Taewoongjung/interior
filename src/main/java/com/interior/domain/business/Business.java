package com.interior.domain.business;

import static com.interior.adapter.common.exception.ErrorType.DUPLICATE_PROGRESS_VALUE;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_RELATED_COMPANY_TO_BUSINESS;
import static com.interior.adapter.common.exception.ErrorType.INVALID_BUSINESS_NAME;
import static com.interior.util.CheckUtil.check;
import static com.interior.util.CheckUtil.require;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.interior.adapter.common.exception.ErrorType;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.business.progress.BusinessProgress;
import com.interior.domain.business.progress.ProgressType;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Business {

    private Long id;

    private String name;

    private Long companyId;

    private Long customerId;

    private BoolType isDeleted;

    private String zipCode;

    private String address;

    private String subAddress;

    private String buildingNumber;

    private List<BusinessMaterial> businessMaterialList;

    private List<BusinessProgress> businessProgressList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastModified;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private Business(
            final Long id,
            final String name,
            final Long companyId,
            final Long customerId,
            final BoolType isDeleted,
            final String zipCode,
            final String address,
            final String subAddress,
            final String buildingNumber,
            final List<BusinessMaterial> businessMaterialList,
            final List<BusinessProgress> businessProgressList,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.customerId = customerId;
        this.isDeleted = isDeleted;
        this.zipCode = zipCode;
        this.address = address;
        this.subAddress = subAddress;
        this.buildingNumber = buildingNumber;
        this.businessMaterialList = businessMaterialList;
        this.businessProgressList = businessProgressList;
        this.lastModified = lastModified;
        this.createdAt = createdAt;
    }

    public static Business of(
            final Long id,
            final String name,
            final Long companyId,
            final Long customerId,
            final BoolType isDeleted,
            final String zipCode,
            final String address,
            final String subAddress,
            final String buildingNumber,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt,
            final List<BusinessMaterial> businessMaterialList,
            final List<BusinessProgress> businessProgressList
    ) {

        check(name == null || "".equals(name.trim()), ErrorType.INVALID_BUSINESS_NAME);
        check(name.toCharArray().length < 1, INVALID_BUSINESS_NAME);
        require(o -> companyId == null, companyId, EMPTY_RELATED_COMPANY_TO_BUSINESS);

        return new Business(id, name, companyId, customerId, isDeleted, zipCode, address,
                subAddress, buildingNumber, businessMaterialList, businessProgressList,
                lastModified, createdAt);
    }

    public static Business of(
            final Long id,
            final String name,
            final Long companyId,
            final Long customerId,
            final BoolType isDeleted,
            final String zipCode,
            final String address,
            final String subAddress,
            final String buildingNumber,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {

        check(name == null || "".equals(name.trim()), ErrorType.INVALID_BUSINESS_NAME);
        check(name.toCharArray().length < 1, INVALID_BUSINESS_NAME);
        require(o -> companyId == null, companyId, EMPTY_RELATED_COMPANY_TO_BUSINESS);

        return new Business(id, name, companyId, customerId, isDeleted, zipCode, address,
                subAddress, buildingNumber, null, null, lastModified, createdAt);
    }

    public void setName(final String name) {
        check(name == null || "".equals(name.trim()), INVALID_BUSINESS_NAME);

        this.name = name;
    }

    public void updateBusinessProgress(final ProgressType updateProgressType) {

        check(this.getBusinessProgressList().stream()
                        .anyMatch(f -> f.getProgressType().equals(updateProgressType)),
                DUPLICATE_PROGRESS_VALUE);

        this.getBusinessProgressList()
                .add(BusinessProgress.of(this.getId(), updateProgressType));
    }
}
