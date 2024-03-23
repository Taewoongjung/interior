package com.interior.adapter.config;

import com.interior.application.security.UserDetailService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final UserDetailService userDetailService;

	@Bean
	public SecurityFilterChain filterChain(final @NotNull HttpSecurity http) throws Exception {
		return http
				.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsFilter()))
				.csrf(AbstractHttpConfigurer::disable)
				.headers((headerConfig) -> headerConfig.frameOptions(FrameOptionsConfig::disable))
				.authorizeHttpRequests((authorizeRequests) ->
								authorizeRequests
										.requestMatchers("/", "/api/login/**").permitAll()
										.requestMatchers("/", "/api/signup/**").permitAll()
										.requestMatchers("/", "/actuator/**").permitAll()
//              .requestMatchers("/posts/**", "/api/v1/posts/**").hasRole(Role.USER.name())
//              .requestMatchers("/admins/**", "/api/v1/admins/**").hasRole(Role.ADMIN.name())
										.anyRequest().authenticated()
				)

				.build();
	}

	@Bean
	public CorsConfigurationSource corsFilter(){
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); // 자바스크립트 응답을 처리할 수 있게 할지 설정(ajax, axios)
		config.addAllowedOrigin("http://localhost:80/");
		config.addAllowedOrigin("http://0.0.0.0:80/");
		config.addAllowedOrigin("http://localhost:3000/");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/api/*", config);
		source.registerCorsConfiguration("/actuator/*", config);
		// TODO /api/* 으로 요청이 왔을때, Allowed 된 요청만 받는지 확인하기

		return source;
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
