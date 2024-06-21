package com.interior.domain.business.material.log;

import java.util.Arrays;
import java.util.NoSuchElementException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessMaterialChangeFieldType {
    CREATE_NEW_MATERIAL("재료생성"),
    DELETE_MATERIAL("재료삭제"),
    MATERIAL_NAME("사업 재료명 수정"),
    MATERIAL_CATEGORY("사업 재료 카테고리 수정"),
    MATERIAL_AMOUNT("사업 재료 수량 수정"),
    MATERIAL_UNIT("사업 재료 수량 단위 수정"),
    MATERIAL_MEMO("사업 재료 메모 수정"),
    MATERIAL_COST_PER_UNIT("사업 재료의 단가 수정"),
    MATERIAL_LABOR_COST_PER_UNIT("사업 재료 노무비 단가 수정");

    private final String desc;

    public static BusinessMaterialChangeFieldType from(final String target) {
        return Arrays.stream(values())
                .filter(f -> target.equals(f.name()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "BusinessMaterialChangeFieldType 에 속한 필드가 없습니다"));
    }

}
