package com.interior.adapter.inbound.user.webdto;

import com.interior.domain.auth.JwtToken;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class LogInDto {

    public record LogInReqDto(@NotNull @Email String email, @NotNull String password) {}

    public record LogInResDto(boolean isLogInSuccess, JwtToken jwtToken) {}
}
