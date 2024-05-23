package com.interior.domain.business;

import java.util.Arrays;
import java.util.NoSuchElementException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessStatus {

    CREATED("생성됨"),
    IN_PROGRESS("진행중"),
    INSPECTION("검수중"),
    COMPLETED("완료"),
    HOLDING("보류")
    ;

    private final String desc;

    public static BusinessStatus from(final String target) {
        return Arrays.stream(values())
                .filter(f -> target.equals(f.desc))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당 사업 상태값이 존재하지 않습니다."));
    }
}
