package com.interior.domain.business.log;

import java.util.Arrays;
import java.util.NoSuchElementException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessChangeFieldType {
    BUSINESS_NAME("사업명"),
    CREATE_NEW_BUSINESS("생성"),
    DELETE_BUSINESS("삭제");

    private final String desc;

    public static BusinessChangeFieldType from(final String target) {
        return Arrays.stream(values())
                .filter(f -> target.equals(f.name()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "BusinessChangeFieldType 에 속한 필드가 없습니다"));
    }

}
