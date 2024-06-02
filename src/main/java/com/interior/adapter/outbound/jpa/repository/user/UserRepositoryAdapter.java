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
import org.springframework.transaction.annotation.Transactional;

@Getter
@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(final String email) {
        UserEntity entity = userJpaRepository.findByEmail(email);
        return entity.toPojo();
    }

    @Override
    @Transactional(readOnly = true)
    public void checkIfExistUserByEmail(final String email) {

        check(!userJpaRepository.existsByEmail(email), INVALID_SIGNUP_REQUEST_DUPLICATE_EMAIL);
    }

    @Override
    @Transactional
    public User save(final User user) {
        UserEntity entity = userJpaRepository.save(userToEntity(user));
        return entity.toPojoWithRelations();
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByEmail(final String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(final Long id) {
        UserEntity entity = userJpaRepository.findById(id).orElse(null);

        check(entity == null, NOT_EXIST_CUSTOMER);

        return entity.toPojo();
    }
}
