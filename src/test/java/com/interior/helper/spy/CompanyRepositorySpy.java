package com.interior.helper.spy;

import static com.interior.adapter.common.exception.ErrorType.COMPANY_NOT_EXIST_IN_THE_USER;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_CUSTOMER;
import static com.interior.util.CheckUtil.check;
import static company.CompanyFixture.COMPANY_LIST;
import static company.CompanyFixture.COMPANY_LIST_OVER_5;

import com.interior.domain.company.Company;
import com.interior.domain.company.repository.CompanyRepository;
import com.interior.domain.user.User;
import com.interior.domain.user.UserRole;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CompanyRepositorySpy implements CompanyRepository {

    @Override
    public Company findById(Long companyId) {
        return null;
    }

    @Override
    public boolean save(String userEmail, Company createCompany) {

        List<User> userList = getUserListForTest();

        userList.stream()
                .filter(f -> userEmail.equals(f.getEmail()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_CUSTOMER.getMessage()));

        /* save */

        return true;
    }

    @Override
    public boolean delete(Long userId, Long companyId) {

        List<User> userList = getUserListForTest();

        User user = userList.stream()
                .filter(f -> userId.equals(f.getId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_CUSTOMER.getMessage()));

        check(user.getCompanyList().stream().noneMatch(f -> companyId.equals(f.getId())),
                COMPANY_NOT_EXIST_IN_THE_USER);

        user.getCompanyList().stream().filter(f -> companyId.equals(f.getId()))
                .forEach(Company::delete);

        return true;
    }

    private List<User> getUserListForTest() {
        List<User> userList = new ArrayList<>();

        userList.add(User.of(
                10L,
                "홍길동",
                "a@a.com",
                "asdqeer1r12jiudjd^312&2ews",
                "01012345678",
                UserRole.ADMIN,
                LocalDateTime.of(2024, 5, 19, 23, 30),
                LocalDateTime.of(2024, 5, 19, 23, 30),
                COMPANY_LIST()
        ));

        userList.add(User.of(
                12L,
                "홍길동",
                "ss@sss.com",
                "asdqeer1r12jiudjd^312&2ews",
                "01012345678",
                UserRole.ADMIN,
                LocalDateTime.of(2024, 5, 19, 23, 30),
                LocalDateTime.of(2024, 5, 19, 23, 30),
                COMPANY_LIST_OVER_5()
        ));

        return userList;
    }
}
