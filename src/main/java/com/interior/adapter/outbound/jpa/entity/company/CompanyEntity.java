package com.interior.adapter.outbound.jpa.entity.company;

import com.interior.adapter.outbound.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Table(name = "company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String subAddress;
    private String buildingNumber;
    private String tel;

    public CompanyEntity(
        final Long id,
        final String name,
        final String address,
        final String subAddress,
        final String buildingNumber,
        final String tel
    ) {
        super(LocalDateTime.now(), LocalDateTime.now());

        this.id = id;
        this.name = name;
        this.address = address;
        this.subAddress = subAddress;
        this.buildingNumber = buildingNumber;
        this.tel = tel;
    }

    public CompanyEntity of(
            final String name,
            final String address,
            final String subAddress,
            final String buildingNumber,
            final String tel
    ) {
        return new CompanyEntity(null, name, address, subAddress, buildingNumber, tel);
    }
}
