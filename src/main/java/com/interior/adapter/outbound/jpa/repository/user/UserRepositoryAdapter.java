package com.interior.adapter.outbound.jpa.repository.user;

import static com.interior.adapter.common.exception.ErrorType.INVALID_SIGNUP_REQUEST_DUPLICATE_EMAIL;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_CUSTOMER;
import static com.interior.util.CheckUtil.check;
import static com.interior.util.converter.jpa.user.UserEntityConverter.userToEntity;

import com.interior.adapter.outbound.jpa.entity.user.UserEntity;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Getter
@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User findByEmail(final String email) {
        UserEntity entity = userJpaRepository.findByEmail(email);
        return entity.toPojo();
    }

    @Override
    public void checkIfExistUserByEmail(final String email) {
        UserEntity entity = userJpaRepository.findByEmail(email);

        check(entity != null, INVALID_SIGNUP_REQUEST_DUPLICATE_EMAIL);
    }

    @Override
    public User save(final User user) {
        UserEntity entity = userJpaRepository.save(userToEntity(user));
        return entity.toPojo();
    }

    @Override
    public Boolean existsByEmail(final String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public User findById(final Long id) {
        UserEntity entity = userJpaRepository.findById(id).orElse(null);

        check(entity == null, NOT_EXIST_CUSTOMER);

        return entity.toPojo();
    }
}
