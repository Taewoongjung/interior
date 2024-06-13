package com.interior.domain.business.contract;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContractType {
    SIGNED("계약 승인"),
    REFUSED("계약 거부");

    private final String type;
}
