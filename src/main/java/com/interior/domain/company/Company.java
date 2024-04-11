package com.interior.domain.company;

import static com.interior.adapter.common.exception.ErrorType.INVALID_COMPANY_NAME;
import static com.interior.adapter.common.exception.ErrorType.INVALID_COMPANY_OWNER_ID;
import static com.interior.adapter.common.exception.ErrorType.INVALID_COMPANY_TEL;
import static com.interior.util.CheckUtil.require;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Company {

    private Long id;
    private String name;
    private Long ownerId;
    private String address;
    private String subAddress;
    private String buildingNumber;
    private String tel;

    private Company(
            final Long id,
            final String name,
            final Long ownerId,
            final String address,
            final String subAddress,
            final String buildingNumber,
            final String tel
    ) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.address = address;
        this.subAddress = subAddress;
        this.buildingNumber = buildingNumber;
        this.tel = tel;
    }

    public static Company of(
            final Long id,
            final String name,
            final Long ownerId,
            final String address,
            final String subAddress,
            final String buildingNumber,
            final String tel
    ) {

        require(o-> name == null, name, INVALID_COMPANY_NAME);
        require(o-> ownerId == null, ownerId, INVALID_COMPANY_OWNER_ID);
        require(o -> tel == null, tel, INVALID_COMPANY_TEL);

        return new Company(id, name, ownerId, address, subAddress, buildingNumber, tel);
    }

    public static Company of(
            final String name,
            final Long ownerId,
            final String address,
            final String subAddress,
            final String buildingNumber,
            final String tel
    ) {

        require(o-> name == null, name, INVALID_COMPANY_NAME);
        require(o -> tel == null, tel, INVALID_COMPANY_TEL);

        return new Company(null, name, ownerId, address, subAddress, buildingNumber, tel);
    }
}