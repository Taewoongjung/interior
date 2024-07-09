package com.interior.application.unittest.command.user.handlers;

import com.interior.application.readmodel.user.handlers.VerifyUserQueryHandler;
import com.interior.application.readmodel.user.queries.VerifyUserQuery;
import com.interior.domain.user.repository.UserRepository;
import com.interior.helper.spy.UserRepositorySpy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_USER;
import static com.interior.adapter.common.exception.ErrorType.NOT_MATCHED_EMAIL_WITH_PHONE_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("VerifyUserQueryHandler 는 ")
class VerifyUserQueryHandlerTest {

    private final UserRepository userRepository = new UserRepositorySpy();

    private final VerifyUserQueryHandler sut = new VerifyUserQueryHandler(userRepository);


    @Test
    @DisplayName("이메일과 휴대폰 번호가 일치하는지 검증한다.")
    void test1() {

        // given
        String email = "a@a.com";
        String phoneNumber = "01012345678";

        VerifyUserQuery event = new VerifyUserQuery(email, phoneNumber);

        // when
        boolean actual = sut.handle(event);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("해당하는 이메일이 없으면 에러를 return 한다.")
    void test2() {

        // given
        String email = "notexist@notexist.com";
        String phoneNumber = "01012345678";

        VerifyUserQuery event = new VerifyUserQuery(email, phoneNumber);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(Exception.class)
                .hasMessage(NOT_EXIST_USER.getMessage());
    }

    @Test
    @DisplayName("해당하는 이메일과 휴대폰 번호가 매핑된 유저가 없으면 에러를 return 한다.")
    void test3() {

        // given
        String email = "a@a.com";
        String phoneNumber = "notexist";

        VerifyUserQuery event = new VerifyUserQuery(email, phoneNumber);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(Exception.class)
                .hasMessage(NOT_MATCHED_EMAIL_WITH_PHONE_NUMBER.getMessage());
    }
}