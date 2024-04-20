package com.interior.domain.company;

import static com.interior.adapter.common.exception.ErrorType.INVALID_COMPANY_NAME;
import static com.interior.adapter.common.exception.ErrorType.INVALID_COMPANY_OWNER_ID;
import static com.interior.adapter.common.exception.ErrorType.INVALID_COMPANY_TEL;
import static com.interior.util.CheckUtil.check;
import static com.interior.util.CheckUtil.require;

import com.interior.adapter.common.exception.ErrorType;
import com.interior.domain.business.Business;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Company {

    private Long id;
    private String name;
    private String zipCode;
    private Long ownerId;
    private String address;
    private String subAddress;
    private String buildingNumber;
    private String tel;
    private LocalDateTime lastModified;
    private LocalDateTime createdAt;

    private List<Business> businessList;

    private Company(
            final Long id,
            final String name,
            final String zipCode,
            final Long ownerId,
            final String address,
            final String subAddress,
            final String buildingNumber,
            final String tel,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt,
            final List<Business> businessList
    ) {
        this.id = id;
        this.name = name;
        this.zipCode = zipCode;
        this.ownerId = ownerId;
        this.address = address;
        this.subAddress = subAddress;
        this.buildingNumber = buildingNumber;
        this.tel = tel;
        this.lastModified = lastModified;
        this.createdAt = createdAt;
        this.businessList = businessList;
    }

    public static Company of(
            final Long id,
            final String name,
            final String zipCode,
            final Long ownerId,
            final String address,
            final String subAddress,
            final String buildingNumber,
            final String tel,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {

        require(o-> name == null, name, INVALID_COMPANY_NAME);
        require(o-> ownerId == null, ownerId, INVALID_COMPANY_OWNER_ID);
        require(o -> tel == null, tel, INVALID_COMPANY_TEL);

        return new Company(id, name, zipCode, ownerId, address, subAddress, buildingNumber, tel, lastModified, createdAt, null);
    }

    public static Company of(
            final String name,
            final String zipCode,
            final Long ownerId,
            final String address,
            final String subAddress,
            final String buildingNumber,
            final String tel,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {

        require(o-> name == null, name, INVALID_COMPANY_NAME);
        require(o -> tel == null, tel, INVALID_COMPANY_TEL);

        return new Company(null, name, zipCode, ownerId, address, subAddress, buildingNumber, tel, lastModified, createdAt, null);
    }

    public static Company of(
            final Long id,
            final String name,
            final String zipCode,
            final Long ownerId,
            final String address,
            final String subAddress,
            final String buildingNumber,
            final String tel,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt,
            final List<Business> businessList
    ) {

        require(o-> name == null, name, INVALID_COMPANY_NAME);
        require(o -> tel == null, tel, INVALID_COMPANY_TEL);

        return new Company(id, name, zipCode, ownerId, address, subAddress, buildingNumber, tel, lastModified, createdAt, businessList);
    }

    public void validateDuplicateName(final String targetName) {

        boolean isExist = getBusinessList().stream()
                .anyMatch(business -> targetName.equals(business.getName()));

        check(isExist, ErrorType.DUPLICATE_BUSINESS_NAME);
    }
}