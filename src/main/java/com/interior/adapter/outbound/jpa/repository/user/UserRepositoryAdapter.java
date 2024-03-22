package com.interior.adapter.outbound.jpa.repository.user;

import static com.interior.adapter.outbound.util.converter.jpa.user.UserEntityConverter.userToEntity;

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
    public User save(final User user) {
        UserEntity entity = userJpaRepository.save(userToEntity(user));
        return entity.toPojo();
    }
}
