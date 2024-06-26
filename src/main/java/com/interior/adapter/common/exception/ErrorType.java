package com.interior.adapter.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    // 도메인 depth 순서로 작성

    INAPPROPRIATE_REQUEST(1, "부적절한 요청 입니다."),

    LOGIN_FAIL(100, "로그인 실패, 아이디 또는 비밀번호를 확인해주세요."),
    EXPIRED_ACCESS_TOKEN(101, "만료 된 토큰입니다."),
    INVALID_SIGNUP_REQUEST_DUPLICATE_EMAIL(102, "이미 존재하는 이메일 입니다."),
    INVALID_SIGNUP_REQUEST_DUPLICATE_TEL(103, "이미 존재하는 휴대폰번호 입니다."),
    LIMIT_OF_COMPANY_COUNT_IS_FIVE(104, "유저당 최고 5개의 회사만 등록 가능합니다."),

    UNABLE_TO_SEND_EMAIL(200, "이메일 보내기에 실패했습니다."),
    INVALID_EMAIL_CHECK_NUMBER(201, "캐시에 설정 된 이메일에 대한 인증번호가 잘못됐습니다."),
    EXPIRED_EMAIL_CHECK_REQUEST(202, "이메일 검증 확인 시간이 지났습니다."),
    NOT_6DIGIT_VERIFY_NUMBER(203, "6자리 인증숫자가 아닙니다."),
    EMPTY_VERIFY_NUMBER(204, "인증숫자가 빈값입니다."),

    UNABLE_TO_SEND_SMS(300, "SMS 보내기에 실패했습니다."),
    INVALID_PHONE_CHECK_NUMBER(301, "캐시에 설정 된 휴대폰에 대한 인증번호가 잘못됐습니다."),
    EXPIRED_PHONE_CHECK_REQUEST(302, "휴대폰 검증 확인 시간이 지났습니다."),
    EMPTY_VALIDATION_NUMBER(303, "템플릿에 인증번호는 필수입니다."),

    NOT_EXIST_CUSTOMER(1000, "유저가 존재하지 않습니다."),
    INVALID_CUSTOMER_NAME(1001, "올바르지 않은 이름입니다."),
    INVALID_CUSTOMER_EMAIL(1002, "올바르지 않은 이메일입니다."),
    INVALID_CUSTOMER_TEL(1003, "올바르지 않은 전화번호입니다."),
    INVALID_CUSTOMER_PASSWORD(1004, "올바르지 않은 비밀번호입니다."),
    INVALID_CUSTOMER_USER_ROLE(1005, "올바르지 않은 유저역할입니다."),
    NOT_VERIFIED_PHONE(1006, "휴대폰 검증이 선행 되어야 합니다."),

    NOT_EXIST_COMPANY(1050, "사업체가 존재하지 않습니다."),
    INVALID_COMPANY_NAME(1051, "올바르지 않은 사업체 명 입니다."),
    INVALID_COMPANY_OWNER_ID(1052, "소유자 정보는 필수입니다."),
    INVALID_COMPANY_TEL(1053, "올바르지 않은 전화번호입니다."),
    COMPANY_NOT_EXIST_IN_THE_USER(1054, "삭제 하려고 하는 사업체를 가지고 있지 않습니다."),

    NOT_EXIST_BUSINESS(1100, "사업이 존재하지 않습니다."),
    INVALID_BUSINESS_NAME(1101, "올바르지 않은 사업명 입니다."),
    DUPLICATE_BUSINESS_NAME(1102, "이미 해당 사업이 존재합니다.\n다시 입력해주세요."),
    EMPTY_BUSINESS_NAME(1103, "사업명이 존재하지 않습니다"),
    EMPTY_RELATED_COMPANY_TO_BUSINESS(1104, "연관 된 회사 정보는 필수값입니다."),

    NOT_EXIST_BUSINESS_MATERIAL(1200, "사업재료가 존재하지 않습니다."),
    EMPTY_USAGE_CATEGORY_INVALID(1201, "재료의 공사 구분에 빈 값은 허용하지 않습니다."),
    EMPTY_BUSINESS_MATERIAL_NAME(1202, "재료 명은 필수값입니다."),
    EMPTY_RELATED_BUSINESS_TO_BUSINESS_MATERIAL(1203, "연관 된 사업 정보는 필수값입니다."),
    EMPTY_BUSINESS_MATERIAL_CATEGORY(1204, "재료의 카테고리는 필수값입니다."),
    EMPTY_BUSINESS_MATERIAL_AMOUNT(1205, "재료의 수량은 필수값입니다."),
    NOT_CONTAIN_MATERIAL_IN_THE_BUSINESS(1206, "해당 사업에 포함 된 재료가 아닙니다."),

    EMPTY_BUSINESS_ID(1300, "사업의 id는 필수값 입니다."),
    EMPTY_BUSINESS_MATERIAL_ID(1301, "사업 재료의 id는 필수값 입니다."),
    EMPTY_CHANGE_FIELD(1302, "변경된 값은 필수값 입니다."),
    EMPTY_UPDATER_ID(1303, "변경자의 id는 필수값 입니다."),
    EMPTY_UPDATER_NAME(1304, "변경자의 이름은 필수값 입니다."),
    DUPLICATE_PROGRESS_VALUE(1305, "이미 해당 상태값이 존재하여 처리 된 이력이 있습니다."),

    NOT_EXIST_SMS_SEND_RESULT(1400, "sms 발송 정보가 없습니다.");


    private final int code;
    private final String message;
}
