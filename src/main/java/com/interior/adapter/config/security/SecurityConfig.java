package com.interior.adapter.config.security;

import com.interior.adapter.config.security.jwt.JWTFilter;
import com.interior.adapter.config.security.jwt.JWTUtil;
import com.interior.adapter.config.security.jwt.LoginFilter;
import com.interior.application.security.UserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//	private final RedisTemplate<String,String> redisTemplate;
	private final UserDetailService userDetailService;
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JWTUtil jwtUtil;

	@Value("${server.front.origin-url}")
	private String frontOriginUrl;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

				CorsConfiguration configuration = new CorsConfiguration();

				configuration.setAllowedOrigins(Arrays.asList(frontOriginUrl));
				configuration.setAllowedMethods(Collections.singletonList("*"));
				configuration.setAllowCredentials(true);
				configuration.setAllowedHeaders(Collections.singletonList("*"));
				configuration.setMaxAge(3600L);

				return configuration;
			}
		})));

		http.httpBasic(AbstractHttpConfigurer::disable);
		
		http.authorizeHttpRequests((auth) -> auth
				.requestMatchers(HttpMethod.GET,
						"/actuator/health"
						,"/api/me"
				).permitAll()
				.requestMatchers(HttpMethod.POST,
						"/api/login"
						,"/api/signup"
				).permitAll()
				.requestMatchers("/admin").hasRole("CUSTOMER")
				.requestMatchers("/login").permitAll()
				.anyRequest().authenticated())
				.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class)
				
				// 로그인 필터 앞에서 JWTFilter 검증
				.addFilterAt(
					new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
					UsernamePasswordAuthenticationFilter.class
				)
				.sessionManagement((session) -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
//		http
//			.formLogin(formLogin -> formLogin
//				.loginProcessingUrl("/api/login").permitAll()
//			);
		
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	/**
	 * 비밀번호 평문 저장을 방지하기 위한 인코더 빈등록
	 * */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
