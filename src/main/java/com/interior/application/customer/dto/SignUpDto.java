package com.interior.application.customer.dto;

public class SignUpDto {

    public static record SignUpReqDto(String name, String password, String email, String tel) {}
    
    public static record SignUpResDto(Boolean isSuccess, String SignedUpUserName) {}
}
