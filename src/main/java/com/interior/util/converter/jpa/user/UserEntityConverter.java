package com.interior.util.converter.jpa.user;

import com.interior.adapter.outbound.jpa.entity.company.CompanyEntity;
import com.interior.adapter.outbound.jpa.entity.user.UserEntity;
import com.interior.domain.user.User;
import com.interior.util.converter.jpa.company.CompanyEntityConverter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserEntityConverter {

    public static UserEntity userToEntity(final User user) {

        Set<CompanyEntity> companyList = new HashSet<>();

        if (user.getCompanyList() != null && !user.getCompanyList().isEmpty()) {

            companyList = user.getCompanyList().stream()
                    .map(CompanyEntityConverter::companyToEntity)
                    .collect(Collectors.toSet());
        }

        return UserEntity.of(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getTel(),
                user.getUserRole(),
                companyList
        );
    }
}
