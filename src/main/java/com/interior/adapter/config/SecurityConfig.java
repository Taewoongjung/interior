package com.interior.adapter.config;

import com.interior.application.security.UserDetailService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtProvider jwtProvider;
	private final RedisTemplate<String,String> redisTemplate;
	private final UserDetailService userDetailService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable();
		http.cors().disable();

		http.formLogin()
				.loginPage("/login")
				.usernameParameter("loginId")
				.usernameParameter("password")
				.successForwardUrl("/");

		http.headers().frameOptions().sameOrigin();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	private AbstractAuthenticationProcessingFilter customAuthenticationFilter() throws Exception {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager(null), jwtProvider);

		//Filter 적용 url
		customAuthenticationFilter.setFilterProcessesUrl("/auth/login");
		//인증 실패시 작동할 FailurHandler
		customAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());

		return customAuthenticationFilter;
	}


	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new JwtFailureHandler();

	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder =
				http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.authenticationProvider(authenticationProvider());
		return authenticationManagerBuilder.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		return new JwtAuthenticationProvider(userDetailService, bCryptPasswordEncoder());
	}

	/**
	 * 비밀번호 평문 저장을 방지하기 위한 인코더 빈등록
	 * */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
