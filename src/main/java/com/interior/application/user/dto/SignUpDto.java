package com.interior.application.user.dto;

import com.interior.domain.user.UserRole;

public class SignUpDto {

    public static record SignUpReqDto(String name, String password, String email, String tel, UserRole role) {}
    
    public static record SignUpResDto(Boolean isSuccess, String SignedUpUserName) {}
}
