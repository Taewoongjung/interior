package com.interior.adapter.config;

import com.interior.application.security.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final UserDetailService userDetailService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		return http
			.csrf(AbstractHttpConfigurer::disable)
			.headers((headerConfig) -> headerConfig.frameOptions(FrameOptionsConfig::disable))
			.authorizeHttpRequests((authorizeRequests) ->
				authorizeRequests
					.requestMatchers("/", "/login/**").permitAll()
//					.requestMatchers("/posts/**", "/api/v1/posts/**").hasRole(Role.USER.name())
//					.requestMatchers("/admins/**", "/api/v1/admins/**").hasRole(Role.ADMIN.name())
					.anyRequest().authenticated()
			)
			.build();
	}
	
	// 인증 관리자 관련 설정
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService)
		throws Exception{
		return http.getSharedObject(AuthenticationManagerBuilder.class)
			.userDetailsService(userDetailService) // 사용자 정보 서비스 설정
			.passwordEncoder(bCryptPasswordEncoder)
			.and()
			.build();
	}
	
	// 패스워드 인코더로 사용할 빈 등록
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
