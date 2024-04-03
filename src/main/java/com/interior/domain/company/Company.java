package com.interior.domain.company;

import static com.interior.adapter.common.exception.ErrorType.INVALID_COMPANY_NAME;
import static com.interior.adapter.common.exception.ErrorType.INVALID_COMPANY_TEL;
import static com.interior.util.CheckUtil.check;
import static com.interior.util.CheckUtil.require;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Company {

    private Long id;
    private String name;
    private String address;
    private String subAddress;
    private String buildingNumber;
    private String tel;
    private LocalDateTime lastModified;
    private LocalDateTime createdAt;

    private Company(
            final Long id,
            final String name,
            final String address,
            final String subAddress,
            final String buildingNumber,
            final String tel,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.subAddress = subAddress;
        this.buildingNumber = buildingNumber;
        this.tel = tel;
        this.lastModified = lastModified;
        this.createdAt = createdAt;
    }

    public static Company of(
            final Long id,
            final String name,
            final String address,
            final String subAddress,
            final String buildingNumber,
            final String tel,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {

        require(o-> name == null, name, INVALID_COMPANY_NAME);
        require(o -> tel == null, tel, INVALID_COMPANY_TEL);

        return new Company(id, name, address, subAddress, buildingNumber, tel, lastModified,
                createdAt);
    }
}