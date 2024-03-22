package com.interior.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 유저 역할
@Getter
@RequiredArgsConstructor
public enum UserRole {
	CUSTOMER("CUSTOMER"), // 고객님
	ADMIN("ADMIN");// 관리자
	
	private final String desc;
}
