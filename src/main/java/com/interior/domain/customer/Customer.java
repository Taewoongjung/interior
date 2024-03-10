package com.interior.domain.customer;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Customer {
	
	private long id;
	private String name;
	private String email;
	private String password;
	private String tel;
	private LocalDateTime lastModified;
	private LocalDateTime createdAt;
	
	public Customer(long id, String name, String email, String password, String tel,
		LocalDateTime lastModified, LocalDateTime createdAt) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.tel = tel;
		this.lastModified = lastModified;
		this.createdAt = createdAt;
	}
}