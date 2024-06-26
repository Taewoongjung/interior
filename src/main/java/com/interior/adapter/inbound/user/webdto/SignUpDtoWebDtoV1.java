package com.interior.adapter.inbound.user.webdto;

import com.interior.domain.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class SignUpDtoWebDtoV1 {

    public record Req(
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
    ) {

    }

    public record Res(Boolean isSuccess, String SignedUpUserName) {

    }
}
