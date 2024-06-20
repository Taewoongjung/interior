package com.interior.domain.business.log;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
@RequiredArgsConstructor
public enum BusinessChangeFieldType {

    // Business
    REVISE_BUSINESS_NAME("사업명 수정"),
    CREATE_NEW_BUSINESS("사업 생성"),
    DELETE_BUSINESS("사업 삭제"),

    //BusinessMaterial
    REVISE_BUSINESS_MATERIAL_NAME("사업 재료명 수정"),
    REVISE_BUSINESS_MATERIAL_CATEGORY("사업 재료 카테고리 수정"),
    REVISE_BUSINESS_MATERIAL_AMOUNT("사업 재료 수량 수정"),
    REVISE_BUSINESS_MATERIAL_MEMO("사업 재료 메모 수정"),
    REVISE_BUSINESS_MATERIAL_COST_PER_UNIT("사업 재료의 단가 수정"),
    REVISE_BUSINESS_MATERIAL_LABOR_COST_PER_UNIT("사업 재료 노무비 단가 수정");

    private final String desc;

    public static BusinessChangeFieldType from(final String target) {
        return Arrays.stream(values())
                .filter(f -> target.equals(f.name()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "BusinessChangeFieldType 에 속한 필드가 없습니다"));
    }

}
