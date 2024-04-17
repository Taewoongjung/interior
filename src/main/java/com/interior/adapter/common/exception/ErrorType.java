package com.interior.adapter.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    EXPIRED_ACCESS_TOKEN(0001, "만료 된 토큰입니다."),
    LOGIN_FAIL(0010, "로그인 실패, 아이디 또는 비밀번호를 확인해주세요."),

    INVALID_CUSTOMER_NAME(1001, "올바르지 않은 이름입니다."),
    INVALID_CUSTOMER_EMAIL(1002, "올바르지 않은 이메일입니다."),
    INVALID_CUSTOMER_TEL(1003, "올바르지 않은 전화번호입니다."),
    INVALID_CUSTOMER_PASSWORD(1004, "올바르지 않은 비밀번호입니다."),
    INVALID_CUSTOMER_USER_ROLE(1005, "올바르지 않은 유저역할입니다."),

    INVALID_COMPANY_NAME(1050, "올바르지 않은 이름입니다."),
    INVALID_COMPANY_OWNER_ID(1051, "소유자 정보는 필수입니다."),
    INVALID_COMPANY_TEL(1052, "올바르지 않은 전화번호입니다."),

    INVALID_SIGNUP_REQUEST_DUPLICATE_EMAIL(1100, "이미 존재하는 이메일 입니다."),

    LIMIT_OF_COMPANY_COUNT_IS_FIVE(1201, "유저당 최고 5개의 회사만 등록 가능합니다.")
    ;

    private final int code;
    private final String message;
}
