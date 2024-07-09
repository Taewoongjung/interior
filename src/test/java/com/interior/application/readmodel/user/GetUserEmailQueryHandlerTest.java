package com.interior.application.readmodel.user;

import com.interior.application.readmodel.user.queries.GetUserEmailQuery;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import com.interior.helper.spy.UserRepositorySpy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_CUSTOMER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("GetUserEmailQueryHandler 는 ")
class GetUserEmailQueryHandlerTest {

    private final UserRepository userRepository = new UserRepositorySpy();

    private final GetUserEmailQueryHandler sut = new GetUserEmailQueryHandler(userRepository);


    @Test
    @DisplayName("휴대폰 번호에 해당하는 유저를 조회 후 유효성 검증을한다.")
    void test1() {
        // given
        String phoneNumber = "01012345678";
        GetUserEmailQuery event = new GetUserEmailQuery(phoneNumber);

        // when
        // then
        User actual = sut.handle(event);

        assertThat(actual.getTel()).isEqualTo("01012345678");
    }

    @Test
    @DisplayName("휴대폰 번호에 해당하는 유저가 없으면 에러를 return 한다.")
    void test2() {
        // given
        String phoneNumber = "010notexist";
        GetUserEmailQuery event = new GetUserEmailQuery(phoneNumber);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(Exception.class)
                .hasMessage(NOT_EXIST_CUSTOMER.getMessage());
    }
}