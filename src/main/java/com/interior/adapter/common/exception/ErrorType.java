package com.interior.adapter.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    // 도메인 depth 순서로 작성

    INAPPROPRIATE_REQUEST(1, "부적절한 요청 입니다."),

    LOGIN_FAIL(0010, "로그인 실패, 아이디 또는 비밀번호를 확인해주세요."),
    EXPIRED_ACCESS_TOKEN(0100, "만료 된 토큰입니다."),

    NOT_EXIST_CUSTOMER(1000, "유저가 존재하지 않습니다."),
    INVALID_CUSTOMER_NAME(1001, "올바르지 않은 이름입니다."),
    INVALID_CUSTOMER_EMAIL(1002, "올바르지 않은 이메일입니다."),
    INVALID_CUSTOMER_TEL(1003, "올바르지 않은 전화번호입니다."),
    INVALID_CUSTOMER_PASSWORD(1004, "올바르지 않은 비밀번호입니다."),
    INVALID_CUSTOMER_USER_ROLE(1005, "올바르지 않은 유저역할입니다."),

    NOT_EXIST_COMPANY(1050, "사업체가 존재하지 않습니다."),
    INVALID_COMPANY_NAME(1051, "올바르지 않은 사업체 명 입니다."),
    INVALID_COMPANY_OWNER_ID(1052, "소유자 정보는 필수입니다."),
    INVALID_COMPANY_TEL(1053, "올바르지 않은 전화번호입니다."),
    COMPANY_NOT_EXIST_IN_THE_USER(1054, "삭제 하려고 하는 사업체를 가지고 있지 않습니다."),

    NOT_EXIST_BUSINESS(1100, "사업이 존재하지 않습니다."),
    INVALID_BUSINESS_NAME(1101, "올바르지 않은 사업명 입니다."),
    DUPLICATE_BUSINESS_NAME(1102, "이미 해당 사업이 존재합니다.\n다시 입력해주세요."),
    NOT_EXIST_BUSINESS_NAME(1103, "사업명이 존재하지 않습니다"),
    NOT_EXIST_RELATED_COMPANY(1104, "연관 된 회사 정보가 존재하지 않습니다."),
    NOT_EXIST_RELATED_CUSTOMER(1105, "연관 된 고객 정보가 존재하지 않습니다."),

    NOT_EXIST_BUSINESS_MATERIAL(1200, "사업재료가 존재하지 않습니다."),
    EMPTY_USAGE_CATEGORY_INVALID(1201, "재료의 공사 구분에 빈 값은 허용하지 않습니다."),

    INVALID_SIGNUP_REQUEST_DUPLICATE_EMAIL(1100, "이미 존재하는 이메일 입니다."),

    LIMIT_OF_COMPANY_COUNT_IS_FIVE(1201, "유저당 최고 5개의 회사만 등록 가능합니다.")
    ;

    private final int code;
    private final String message;
}
