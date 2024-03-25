package com.interior.domain.user.repository;

import com.interior.domain.user.User;

public interface UserRepository {
    
    User findByEmail(final String email);

    public void checkIfExistUserByEmail(final String email);

    User save(final User user);

    Boolean existsByEmail(final String email);
}
