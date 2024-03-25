package com.interior.domain.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class JwtToken {

    private final String grantType;
    private final String accessToken;
    private final String refreshToken;
}
