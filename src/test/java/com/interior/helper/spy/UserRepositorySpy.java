package com.interior.helper.spy;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.domain.user.User;
import com.interior.domain.user.UserRole;
import com.interior.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.interior.adapter.common.exception.ErrorType.*;
import static com.interior.util.CheckUtil.check;
import static company.CompanyFixture.COMPANY_LIST;
import static company.CompanyFixture.COMPANY_LIST_OVER_5;

public class UserRepositorySpy implements UserRepository {

    @Override
    public User findByEmail(String email) {
        List<User> userList = getUserListForTest();

        User user = userList.stream()
                .filter(f -> email.equals(f.getEmail()))
                .findFirst()
                .orElseThrow(() -> new InvalidInputException(NOT_EXIST_USER));

        return user;
    }

    @Override
    public void checkIfExistUserByEmail(String email) {
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

        User user = userList.stream()
                .filter(f -> email.equals(f.getEmail()))
                .findFirst().orElse(null);

        check(user != null, INVALID_SIGNUP_REQUEST_DUPLICATE_EMAIL);
    }

    @Override
    public void checkIfExistUserByPhoneNumber(String tel) {
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
                "01011112222",
                UserRole.ADMIN,
                LocalDateTime.of(2024, 5, 19, 23, 30),
                LocalDateTime.of(2024, 5, 19, 23, 30),
                COMPANY_LIST_OVER_5()
        ));

        User user = userList.stream()
                .filter(f -> tel.equals(f.getTel()))
                .findFirst().orElse(null);

        check(user != null, INVALID_SIGNUP_REQUEST_DUPLICATE_TEL);
    }

    @Override
    public User save(User user) {
        return User.of(
                12L,
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getTel(),
                user.getUserRole(),
                LocalDateTime.of(2024, 5, 19, 23, 30),
                LocalDateTime.of(2024, 5, 19, 23, 30)
        );
    }

    @Override
    public User findById(Long id) {

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

        User entity = userList.stream().filter(f -> id.equals(f.getId())).findFirst()
                .orElse(null);

        check(entity == null, NOT_EXIST_CUSTOMER);

        return entity;
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {

        List<User> userList = getUserListForTest();

        return userList.stream()
                .filter(f -> phoneNumber.equals(f.getTel()))
                .findFirst()
                .orElseThrow(() -> new InvalidInputException(NOT_EXIST_CUSTOMER));
    }

    @Override
    public boolean reviseUserPassword(final String email, final String phoneNumber, String password) {

        User user = findByEmail(email);

        check(!phoneNumber.equals(user.getTel()), NOT_EXIST_CUSTOMER);

        /* 엔티티 도메인 비즈니스 모델로 비밀번호 재설정 */

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
                "01088257754",
                UserRole.ADMIN,
                LocalDateTime.of(2024, 5, 19, 23, 30),
                LocalDateTime.of(2024, 5, 19, 23, 30),
                COMPANY_LIST_OVER_5()
        ));

        return userList;
    }
}
