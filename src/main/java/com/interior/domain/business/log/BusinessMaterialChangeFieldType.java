package com.interior.domain.business.log;

import java.util.Arrays;
import java.util.NoSuchElementException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessMaterialChangeFieldType {

    NAME("이름"),
    CATEGORY("카테고리");

    private final String desc;

    public static BusinessMaterialChangeFieldType from(final String target) {
        return Arrays.stream(values())
                .filter(f -> target.equals(f.name()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "BusinessMaterialChangeFieldType 에 속한 필드가 없습니다"));
    }

}
