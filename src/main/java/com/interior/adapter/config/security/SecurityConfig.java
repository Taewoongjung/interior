package com.interior.adapter.config.security;

import com.interior.adapter.config.security.jwt.JWTFilter;
import com.interior.adapter.config.security.jwt.JWTUtil;
import com.interior.adapter.config.security.jwt.LoginFilter;
import com.interior.application.security.UserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
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

	private final RedisTemplate<String,String> redisTemplate;
	private final UserDetailService userDetailService;
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JWTUtil jwtUtil;

	@Value("${server.front.origin-url}")
	private String frontOriginUrl;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable);

		http.cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

				CorsConfiguration configuration = new CorsConfiguration();

				configuration.setAllowedOrigins(Collections.singletonList("/**"));
				configuration.setAllowedMethods(Collections.singletonList("*"));
				configuration.setAllowPrivateNetwork(true);
				configuration.setAllowCredentials(true);
				configuration.setAllowedHeaders(Collections.singletonList("*"));
				configuration.setMaxAge(3600L);

				configuration.setExposedHeaders(Collections.singletonList("Authorization"));

				return configuration;
			}
		})));

		http.formLogin(AbstractHttpConfigurer::disable);

		http.httpBasic(AbstractHttpConfigurer::disable);

		http.authorizeHttpRequests((auth) -> auth
				.requestMatchers(HttpMethod.GET,
						"/actuator/health",
						"api/*"
				).permitAll()
				.requestMatchers(HttpMethod.POST,
						"/login",
						"/api/signup"
				).permitAll()
				.requestMatchers("/admin").hasRole("CUSTOMER")
				.anyRequest().authenticated());

		// 로그인 필터 앞에서 JWTFilter 검증
		http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

		http.addFilterAt(
				new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
				UsernamePasswordAuthenticationFilter.class
		);

		http.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

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
