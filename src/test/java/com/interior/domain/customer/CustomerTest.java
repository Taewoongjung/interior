package com.interior.domain.customer;

import static customer.CustomerFixture.CUSTOMER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Customer 는 ")
class CustomerTest {

	@Test
	@DisplayName("생성된다.")
	void test1() {
		assertDoesNotThrow(() -> Customer.of(
			CUSTOMER.getId(),
			CUSTOMER.getName(),
			CUSTOMER.getEmail(),
			CUSTOMER.getPassword(),
			CUSTOMER.getTel(),
			CUSTOMER.getLastModified(),
			CUSTOMER.getCreatedAt()
		));
	}
	
	@Test
	@DisplayName("이름이 필수값이다.")
	void test2() {
		assertThatThrownBy(() -> Customer.of(
			CUSTOMER.getId(),
			null,
			CUSTOMER.getEmail(),
			CUSTOMER.getPassword(),
			CUSTOMER.getTel(),
			CUSTOMER.getLastModified(),
			CUSTOMER.getCreatedAt()
		));
	}
	
	@Test
	@DisplayName("이메일이 필수값이다.")
	void test3() {
		assertThatThrownBy(() -> Customer.of(
			CUSTOMER.getId(),
			CUSTOMER.getName(),
			null,
			CUSTOMER.getPassword(),
			CUSTOMER.getTel(),
			CUSTOMER.getLastModified(),
			CUSTOMER.getCreatedAt()
		));
	}
	
	@Test
	@DisplayName("비밀번호가 필수값이다.")
	void test4() {
		assertThatThrownBy(() -> Customer.of(
			CUSTOMER.getId(),
			CUSTOMER.getName(),
			CUSTOMER.getEmail(),
			null,
			CUSTOMER.getTel(),
			CUSTOMER.getLastModified(),
			CUSTOMER.getCreatedAt()
		));
	}
	
	@Test
	@DisplayName("전화번호가 필수값이다.")
	void test5() {
		assertThatThrownBy(() -> Customer.of(
			CUSTOMER.getId(),
			CUSTOMER.getName(),
			CUSTOMER.getEmail(),
			CUSTOMER.getPassword(),
			null,
			CUSTOMER.getLastModified(),
			CUSTOMER.getCreatedAt()
		));
	}
}