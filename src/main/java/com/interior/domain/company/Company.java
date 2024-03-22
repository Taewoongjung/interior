package com.interior.domain.company;

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

    public Company(
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
}