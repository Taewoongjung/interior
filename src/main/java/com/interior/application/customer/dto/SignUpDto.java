package com.interior.application.customer.dto;

public class SignUpDto {

    public static record SignUpReqDto(String name, String password, String email, String tel) {}
}
