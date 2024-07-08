package com.interior.domain.user.repository;

import com.interior.domain.user.User;

public interface UserRepository {

    User findByEmail(final String email);

    void checkIfExistUserByEmail(final String email);

    void checkIfExistUserByPhoneNumber(final String tel);

    User save(final User user);

    User findById(final Long id);
}
