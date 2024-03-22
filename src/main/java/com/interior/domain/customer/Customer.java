package com.interior.domain.customer;

import static com.interior.adapter.common.exception.ErrorType.INVALID_CUSTOMER_EMAIL;
import static com.interior.adapter.common.exception.ErrorType.INVALID_CUSTOMER_NAME;
import static com.interior.adapter.common.exception.ErrorType.INVALID_CUSTOMER_PASSWORD;
import static com.interior.adapter.common.exception.ErrorType.INVALID_CUSTOMER_TEL;
import static com.interior.adapter.outbound.util.CheckUtil.require;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Customer {

	private Long id;
	private String name;
	private String email;
	private String password;
	private String tel;
	private LocalDateTime lastModified;
	private LocalDateTime createdAt;

	private Customer(
			final Long id,
			final String name,
			final String email,
			final String password,
			final String tel,
			final LocalDateTime lastModified,
			final LocalDateTime createdAt
	) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.tel = tel;
		this.lastModified = lastModified;
		this.createdAt = createdAt;
	}

	public static Customer of(
			final String name,
			final String email,
			final String password,
			final String tel,
			final LocalDateTime lastModified,
			final LocalDateTime createdAt
	) {
		
		require(o -> name == null, name, INVALID_CUSTOMER_NAME);
		require(o -> email == null, name, INVALID_CUSTOMER_EMAIL);
		require(o -> password == null, password, INVALID_CUSTOMER_PASSWORD);
		require(o -> tel == null, tel, INVALID_CUSTOMER_TEL);
		
		return new Customer(null, name, email, password, tel, lastModified, createdAt);
	}

	public static Customer of(
			final Long id,
			final String name,
			final String email,
			final String password,
			final String tel,
			final LocalDateTime lastModified,
			final LocalDateTime createdAt
	) {
		
		require(o -> name == null, name, INVALID_CUSTOMER_NAME);
		require(o -> email == null, name, INVALID_CUSTOMER_EMAIL);
		require(o -> password == null, password, INVALID_CUSTOMER_PASSWORD);
		require(o -> tel == null, tel, INVALID_CUSTOMER_TEL);
		
		return new Customer(id, name, email, password, tel, lastModified, createdAt);
	}
}