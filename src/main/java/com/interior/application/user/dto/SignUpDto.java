package com.interior.application.user.dto;

import com.interior.domain.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class SignUpDto {

    public static record SignUpReqDto(
            @NotNull
            String name,

            @NotNull
            String password,

            @NotNull
            @Email
            String email,

            @NotNull
            String tel,

            @NotNull
            UserRole role
    ) {}
    
    public static record SignUpResDto(Boolean isSuccess, String SignedUpUserName) {}
}
