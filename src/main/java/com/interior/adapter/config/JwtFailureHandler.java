package com.interior.adapter.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class JwtFailureHandler implements AuthenticationFailureHandler {

    private final String DEFAULT_FAILURE_URL = "login/error";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        String errorMsg = null;

        if (exception instanceof BadCredentialsException
                || exception instanceof InternalAuthenticationServiceException) {
            errorMsg = "아이디나 비밀번호가맞지 않습니다. 다시 확인해 주세요.";
        } else if (exception instanceof DisabledException) {
            errorMsg = "계정이 비활성화 됐습니다. 관리자에게 문의해주세요.";
        } else if (exception instanceof CredentialsExpiredException) {
            errorMsg = "비밀번 유효기간이 만료되었습니다. 관리자에게 문의해주세요";
        } else {
            errorMsg = "알수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의해주세요";
        }

        request.setAttribute("errorMessage", errorMsg);
        request.getRequestDispatcher(DEFAULT_FAILURE_URL).forward(request, response);
    }
}
