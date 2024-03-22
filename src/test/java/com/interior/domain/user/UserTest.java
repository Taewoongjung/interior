package com.interior.domain.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Customer 는 ")
class UserTest {
	
	@Test
	@DisplayName("생성된다.")
	void test1() {
		assertDoesNotThrow(() -> User.of(
			1L,
			"홍길동",
			"abc@gmail.com",
			"111",
			"01012345678",
			UserRole.ADMIN,
			LocalDateTime.of(2024, 3, 22, 15, 10),
			LocalDateTime.of(2024, 3, 22, 15, 10)
		));
	}
	
	@Test
	@DisplayName("이름이 필수값이다.")
	void test2() {
		assertThatThrownBy(() -> User.of(
			1L,
			null,
			"abc@gmail.com",
			"111",
			"01012345678",
			UserRole.ADMIN,
			LocalDateTime.of(2024, 3, 22, 15, 10),
			LocalDateTime.of(2024, 3, 22, 15, 10)
		));
	}
	
	@Test
	@DisplayName("이메일이 필수값이다.")
	void test3() {
		assertThatThrownBy(() -> User.of(
			1L,
			"홍길동",
			null,
			"111",
			"01012345678",
			UserRole.CUSTOMER,
			LocalDateTime.of(2024, 3, 22, 15, 10),
			LocalDateTime.of(2024, 3, 22, 15, 10)
		));
	}
	
	@Test
	@DisplayName("비밀번호가 필수값이다.")
	void test4() {
		assertThatThrownBy(() -> User.of(
			1L,
			"홍길동",
			"abc@gmail.com",
			null,
			"01012345678",
			UserRole.CUSTOMER,
			LocalDateTime.of(2024, 3, 22, 15, 10),
			LocalDateTime.of(2024, 3, 22, 15, 10)
		));
	}
	
	@Test
	@DisplayName("전화번호가 필수값이다.")
	void test5() {
		assertThatThrownBy(() -> User.of(
			1L,
			"홍길동",
			"abc@gmail.com",
			"111",
			null,
			UserRole.ADMIN,
			LocalDateTime.of(2024, 3, 22, 15, 10),
			LocalDateTime.of(2024, 3, 22, 15, 10)
		));
	}
	
	@Test
	@DisplayName("역할이 필수값이다.")
	void test6() {
		assertThatThrownBy(() -> User.of(
			1L,
			"홍길동",
			"abc@gmail.com",
			"111",
			"01012345678",
			null,
			LocalDateTime.of(2024, 3, 22, 15, 10),
			LocalDateTime.of(2024, 3, 22, 15, 10)
		));
	}
}