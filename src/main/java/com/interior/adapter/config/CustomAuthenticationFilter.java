package com.interior.adapter.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/auth/login", "POST");
    private boolean postOnly = true;

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    private static final String AUTHENTICATION = "Authentication ";
    private static final String PREFIX_BEARER = "Bearer ";
    private static final String EXPIRE = "Expire ";

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");

        if (!this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        else if (Objects.isNull(loginId) || Objects.isNull(password) || loginId.isBlank() || password.isBlank()){
//            throw new InvalidLoginRequestException();
            throw new RuntimeException();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password);

        return authenticationManager.authenticate(authenticationToken);
    }
}
