package com.interior.domain.business.progress;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProgressType {

    CREATED("생성 됨"),
    MAKING_QUOTATION("견적서 작성 중"),
    COMPLETE_QUOTATION("견적서 작성 완료"),
    QUOTATION_REQUESTED("견적서 요청 됨"),
    CONTRACTED("계약 성사 됨"),
    REQUEST_REVISING("견적서 수정 요청 됨"),
    CONTRACT_REFUSED("계약 거부 됨"),
    RECEIVED_CONTRACT_DEPOSIT("계약금 수령 함"), // 총 공사비용의 퍼센테이지와 직접입력으로 입력받기
    IN_DEMOLITION("철거 작업 중"),
    IN_PROGRESS("공사 진행 중"),
    CONSTRUCTION_SUSPENSION("공사 보류"),
    CONSTRUCTION_CONTINUE("공사 재개");

    private final String type;
}
