package com.interior.domain.business.material.log;

import java.util.Arrays;
import java.util.NoSuchElementException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessMaterialChangeFieldType {
    CREATE_NEW_MATERIAL("생성"), // 재료생성
    DELETE_MATERIAL("삭제"), // 재료삭제
    MATERIAL_NAME("이름"),
    MATERIAL_CATEGORY("카테고리");

    private final String desc;

    public static BusinessMaterialChangeFieldType from(final String target) {
        return Arrays.stream(values())
                .filter(f -> target.equals(f.name()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "BusinessMaterialChangeFieldType 에 속한 필드가 없습니다"));
    }

}
