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
    MATERIAL_NAME("재료명"),
    MATERIAL_CATEGORY("재료 카테고리"),
    MATERIAL_AMOUNT("재료 수량"),
    MATERIAL_UNIT("재료 수량 단위"),
    MATERIAL_MEMO("재료 메모"),
    MATERIAL_COST_PER_UNIT("재료의 단가"),
    MATERIAL_LABOR_COST_PER_UNIT("재료 노무비 단가");

    private final String desc;

    public static BusinessMaterialChangeFieldType from(final String target) {
        return Arrays.stream(values())
                .filter(f -> target.equals(f.name()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "BusinessMaterialChangeFieldType 에 속한 필드가 없습니다"));
    }

}
