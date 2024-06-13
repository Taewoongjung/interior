package com.interior.domain.business;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_NAME;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_RELATED_COMPANY_TO_BUSINESS;
import static com.interior.adapter.common.exception.ErrorType.INVALID_BUSINESS_NAME;
import static com.interior.util.CheckUtil.check;
import static com.interior.util.CheckUtil.require;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.interior.domain.business.material.BusinessMaterial;
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
            final List<BusinessMaterial> businessMaterialList
    ) {

        require(o -> name == null, name, EMPTY_BUSINESS_NAME);
        require(o -> companyId == null, companyId, EMPTY_RELATED_COMPANY_TO_BUSINESS);

        return new Business(id, name, companyId, customerId, isDeleted, zipCode, address,
                subAddress, buildingNumber, businessMaterialList, LocalDateTime.now(),
                LocalDateTime.now());
    }

    public static Business of(
            final String name,
            final Long companyId,
            final Long customerId,
            final BoolType isDeleted,
            final String zipCode,
            final String address,
            final String subAddress,
            final String buildingNumber,
            final List<BusinessMaterial> businessMaterialList
    ) {

        require(o -> name == null, name, EMPTY_BUSINESS_NAME);
        require(o -> companyId == null, companyId, EMPTY_RELATED_COMPANY_TO_BUSINESS);

        return new Business(null, name, companyId, customerId, isDeleted, zipCode, address,
                subAddress, buildingNumber, businessMaterialList, LocalDateTime.now(),
                LocalDateTime.now());
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
            final String buildingNumber
    ) {

        require(o -> name == null, name, EMPTY_BUSINESS_NAME);
        require(o -> companyId == null, companyId, EMPTY_RELATED_COMPANY_TO_BUSINESS);

        return new Business(id, name, companyId, customerId, isDeleted, zipCode, address,
                subAddress, buildingNumber, null, LocalDateTime.now(),
                LocalDateTime.now());
    }

    public void setName(final String name) {
        check(name == null || "".equals(name.trim()), INVALID_BUSINESS_NAME);

        this.name = name;
    }
}
