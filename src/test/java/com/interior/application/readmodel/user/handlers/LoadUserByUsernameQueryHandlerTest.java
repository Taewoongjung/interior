package com.interior.application.readmodel.user.handlers;

import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.domain.user.repository.UserRepository;
import com.interior.helper.spy.UserRepositorySpy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

@DisplayName("LoadUserByUsernameQueryHandler 는 ")
class LoadUserByUsernameQueryHandlerTest {

    private final UserRepository userRepository = new UserRepositorySpy();

    private final LoadUserByUsernameQueryHandler sut = new LoadUserByUsernameQueryHandler(
            userRepository);

    @Test
    @DisplayName("email 로 UserDetails 을 조회해서 return 한다.")
    void test1() {

        // given
        String email = "a@a.com";

        // when
        // then
        UserDetails actual = sut.loadUserByUsername(email);

        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo("a@a.com");
    }

    @Test
    @DisplayName("존재하지 않는 email 은 UserDetails 을 조회할 수 없다.")
    void test2() {

        // given
        String email = "asas@sasa.com";

        // when
        // then
        assertThatThrownBy(() -> {
            sut.loadUserByUsername(email);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(NOT_EXIST_USER.getMessage());
    }
}