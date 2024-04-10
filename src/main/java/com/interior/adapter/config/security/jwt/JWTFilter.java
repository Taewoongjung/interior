package com.interior.adapter.config.security.jwt;

import static com.interior.adapter.common.exception.ErrorType.EXPIRED_ACCESS_TOKEN;
import static com.interior.util.CheckUtil.check;

import com.interior.application.security.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        // request 에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {

            log.warn("token null");
            filterChain.doFilter(request, response);

            // 이 조건에 해당되면 메소드 종료
            return;
        }

        String token = jwtUtil.getTokenWithoutBearer(authorization);
        check(jwtUtil.isExpired(token), EXPIRED_ACCESS_TOKEN);

        // 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {

            log.info("token expired");
            filterChain.doFilter(request, response);

            // 이 조건에 해당되면 메소드 종료
            return;
        }

        // 토큰에서 email, role 값 획득
        String email = jwtUtil.getEmail(token);

        // 토큰으로 User 객체 생성
        UserDetails user = userDetailService.loadUserByUsername(email);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(user, null,
                user.getAuthorities());

        // 세선에 사용자 등록 (유저 세선 등록)
        SecurityContextHolder.getContext().setAuthentication(authToken);

        response.setHeader("Authorization", token);
        response.addHeader("Authorization", token);
        filterChain.doFilter(request, response);
    }
}
