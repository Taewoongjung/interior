package com.interior.domain.business;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessStatusDetail {

    START("착수"),
    DESIGNING("설계"),
    DEMOLITION("철거"),
    CONSTRUCTING("공사중"),
    CLEANING("청소중"),
    FINALIZING("마무리작업"),
    COMPLETED("완료"),
    HOLDING("보류"),
    ;

    private final String desc;
}
