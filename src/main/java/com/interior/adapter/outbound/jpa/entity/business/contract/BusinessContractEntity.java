package com.interior.adapter.outbound.jpa.entity.business.contract;

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


//@Entity
//@Getter
//@ToString
//@Table(name = "business_contract")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class BusinessContractEntity extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private BusinessContractEntity(
//            final Long id
//    ) {
//
//        super(LocalDateTime.now(), LocalDateTime.now());
//
//        this.id = id;
//    }
//}
