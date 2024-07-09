package com.interior.adapter.outbound.jpa.repository.user;

import com.interior.adapter.outbound.jpa.entity.company.CompanyEntity;
import com.interior.adapter.outbound.jpa.entity.user.UserEntity;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.interior.adapter.common.exception.ErrorType.*;
import static com.interior.util.CheckUtil.check;
import static com.interior.util.converter.jpa.user.UserEntityConverter.userToEntity;

@Getter
@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(final String email) {
        UserEntity entity = userJpaRepository.findByEmail(email);

        check(entity == null, NOT_EXIST_USER);

        List<CompanyEntity> companyEntityList = entity.getCompanyEntityList();

        return entity.toPojo(companyEntityList);
    }

    @Override
    @Transactional(readOnly = true)
    public void checkIfExistUserByEmail(final String email) {

        check(userJpaRepository.existsByEmail(email), INVALID_SIGNUP_REQUEST_DUPLICATE_EMAIL);
    }

    @Override
    @Transactional(readOnly = true)
    public void checkIfExistUserByPhoneNumber(final String tel) {

        check(userJpaRepository.existsByTel(tel), INVALID_SIGNUP_REQUEST_DUPLICATE_TEL);
    }

    @Override
    @Transactional
    public User save(final User user) {
        UserEntity entity = userJpaRepository.save(userToEntity(user));
        return entity.toPojoWithRelations();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(final Long id) {
        UserEntity entity = userJpaRepository.findById(id).orElse(null);

        check(entity == null, NOT_EXIST_CUSTOMER);

        return entity.toPojo();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByPhoneNumber(final String phoneNumber) {
        UserEntity entity = userJpaRepository.findByTel(phoneNumber);

        check(entity == null, NOT_EXIST_CUSTOMER);

        return entity.toPojo();
    }

    @Override
    @Transactional
    public boolean reviseUserPassword(final String email, final String phoneNumber, final String password) {
        check(!userJpaRepository.existsByEmail(email), NOT_EXIST_USER);

        UserEntity entity = userJpaRepository.findByTel(phoneNumber);

        check(entity == null, NOT_EXIST_CUSTOMER);

        entity.resetPassword(password);

        return true;
    }
}
