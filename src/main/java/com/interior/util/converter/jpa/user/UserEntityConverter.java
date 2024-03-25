package com.interior.util.converter.jpa.user;

import com.interior.adapter.outbound.jpa.entity.user.UserEntity;
import com.interior.domain.user.User;

public class UserEntityConverter {

    public static UserEntity userToEntity(final User user) {
        return UserEntity.of(user.getName(), user.getEmail(), user.getPassword(), user.getTel(), user.getUserRole());
    }
}
