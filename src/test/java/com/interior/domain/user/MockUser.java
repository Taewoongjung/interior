package com.interior.domain.user;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockSecurityContextFactory.class)
public @interface MockUser {

    String name();

    String email();

    String password();

    String tel();

    UserRole userRole();

    int userCompanyCount();
}
