package com.interior.adapter.outbound.jpa.repository.user;

import com.interior.adapter.outbound.jpa.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    //    @EntityGraph(attributePaths = {"companyEntityList"})
//    @Query("SELECT u FROM UserEntity u JOIN FETCH u.companyEntityList")
    UserEntity findByEmail(final String email);

    Boolean existsByEmail(final String email);
}
