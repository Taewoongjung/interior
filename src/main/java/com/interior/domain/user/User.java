package com.interior.domain.user;

import static com.interior.adapter.common.exception.ErrorType.INVALID_CUSTOMER_EMAIL;
import static com.interior.adapter.common.exception.ErrorType.INVALID_CUSTOMER_NAME;
import static com.interior.adapter.common.exception.ErrorType.INVALID_CUSTOMER_PASSWORD;
import static com.interior.adapter.common.exception.ErrorType.INVALID_CUSTOMER_TEL;
import static com.interior.adapter.common.exception.ErrorType.INVALID_CUSTOMER_USER_ROLE;
import static com.interior.util.CheckUtil.require;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@ToString
public class User extends Throwable implements UserDetails {

	private Long id;
	private String name;
	private String email;
	private String password;
	private String tel;
	private UserRole userRole;
	private LocalDateTime lastModified;
	private LocalDateTime createdAt;

	private User(
			final Long id,
			final String name,
			final String email,
			final String password,
			final String tel,
			final UserRole userRole,
			final LocalDateTime lastModified,
			final LocalDateTime createdAt
	) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.tel = tel;
		this.userRole = userRole;
		this.lastModified = lastModified;
		this.createdAt = createdAt;
	}

	public static User of(
			final String name,
			final String email,
			final String password,
			final String tel,
			final UserRole userRole,
			final LocalDateTime lastModified,
			final LocalDateTime createdAt
	) {

		require(o -> name == null, name, INVALID_CUSTOMER_NAME);
		require(o -> email == null, email, INVALID_CUSTOMER_EMAIL);
		require(o -> password == null, password, INVALID_CUSTOMER_PASSWORD);
		require(o -> tel == null, tel, INVALID_CUSTOMER_TEL);
		require(o -> userRole == null, userRole, INVALID_CUSTOMER_USER_ROLE);

		return new User(null, name, email, password, tel, userRole, lastModified, createdAt);
	}

	public static User of(
			final Long id,
			final String name,
			final String email,
			final String password,
			final String tel,
			final UserRole userRole,
			final LocalDateTime lastModified,
			final LocalDateTime createdAt
	) {

		require(o -> name == null, name, INVALID_CUSTOMER_NAME);
		require(o -> email == null, email, INVALID_CUSTOMER_EMAIL);
		require(o -> password == null, password, INVALID_CUSTOMER_PASSWORD);
		require(o -> tel == null, tel, INVALID_CUSTOMER_TEL);
		require(o -> userRole == null, userRole, INVALID_CUSTOMER_USER_ROLE);

		return new User(id, name, email, password, tel, userRole, lastModified, createdAt);
	}

	public static User of(
			final String email,
			final String userRole
	) {
		require(o -> email == null, email, INVALID_CUSTOMER_EMAIL);
		require(o -> userRole == null, userRole, INVALID_CUSTOMER_USER_ROLE);

		return new User(null, null, email, null, null, UserRole.from(userRole), null, null);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("CUSTOMER"));
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}