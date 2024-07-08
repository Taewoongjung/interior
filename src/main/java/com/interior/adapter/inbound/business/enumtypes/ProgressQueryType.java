package com.interior.adapter.inbound.business.enumtypes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProgressQueryType {
    QUOTATION_REQUESTED("견적서 초안 도착"),
    ALL("템플릿 상관 없이 해당하는 히스토리 모두 조회");

    private final String templateName;
}
