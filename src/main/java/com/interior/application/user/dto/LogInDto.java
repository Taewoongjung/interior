package com.interior.application.user.dto;

import com.interior.domain.auth.JwtToken;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class LogInDto {

    public static record LogInReqDto(@NotNull @Email String email, @NotNull String password) {}

    public static record LogInResDto(boolean isLogInSuccess, JwtToken jwtToken) {}
}
