package com.interior.util.converter.jpa.user;

import com.interior.adapter.outbound.jpa.entity.company.CompanyEntity;
import com.interior.adapter.outbound.jpa.entity.user.UserEntity;
import com.interior.domain.user.User;
import com.interior.util.converter.jpa.company.CompanyEntityConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserEntityConverter {

    public static UserEntity userToEntity(final User user) {

        List<CompanyEntity> companyList = new ArrayList<>();

        if (user.getCompanyList() != null && !user.getCompanyList().isEmpty()) {

            companyList = user.getCompanyList().stream()
                    .map(CompanyEntityConverter::companyToEntity)
                    .collect(Collectors.toList());
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
