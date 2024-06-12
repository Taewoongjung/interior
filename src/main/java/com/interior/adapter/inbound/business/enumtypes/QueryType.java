package com.interior.adapter.inbound.business.enumtypes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QueryType {

    none("입력 안됨"),
    business_management("사업관리");

    private final String type;
}
