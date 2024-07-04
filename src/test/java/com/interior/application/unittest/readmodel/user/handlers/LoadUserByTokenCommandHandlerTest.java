package com.interior.application.unittest.readmodel.user.handlers;

import static com.interior.adapter.common.exception.ErrorType.EXPIRED_ACCESS_TOKEN;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.adapter.config.security.jwt.JWTUtil;
import com.interior.application.readmodel.user.handlers.LoadUserByTokenCommandHandler;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import com.interior.helper.spy.UserRepositorySpy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("LoadUserByTokenCommandHandler 는 ")
class LoadUserByTokenCommandHandlerTest {

    private final JWTUtil jwtUtil = mock(JWTUtil.class);
    private final UserRepository userRepository = new UserRepositorySpy();

    private final LoadUserByTokenCommandHandler sut = new LoadUserByTokenCommandHandler(
            jwtUtil, userRepository);


    @Test
    @DisplayName("토큰으로 유저를 찾을 수 있다.")
    void test1() {

        // given
        String reqToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFAYS5jb20iLCJyb2xlIjoiQ1VTVE9NRVIiLCJpYXQiOjE3MjAwNjYxNzEsImV4cCI6MTcyMDA3MzM3MX0.QqQKPMOE70xDalpUnlycVoVh26KoAVPu5BQp5kLAgig";
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFAYS5jb20iLCJyb2xlIjoiQ1VTVE9NRVIiLCJpYXQiOjE3MjAwNjYxNzEsImV4cCI6MTcyMDA3MzM3MX0.QqQKPMOE70xDalpUnlycVoVh26KoAVPu5BQp5kLAgig";

        // when
        when(jwtUtil.getTokenWithoutBearer(reqToken)).thenReturn(token);
        when(jwtUtil.isExpired(token)).thenReturn(false);
        when(jwtUtil.getEmail(token)).thenReturn("a@a.com");

        // then
        User actual = sut.handle(reqToken);

        assertThat(actual.getEmail()).isEqualTo("a@a.com");
    }

    @Test
    @DisplayName("만료 된 토큰으로 유저를 찾을 수 없다.")
    void test2() {

        // given
        String reqToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFAYS5jb20iLCJyb2xlIjoiQ1VTVE9NRVIiLCJpYXQiOjE3MjAwNjYxNzEsImV4cCI6MTcyMDA3MzM3MX0.QqQKPMOE70xDalpUnlycVoVh26KoAVPu5BQp5kLAgig";
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFAYS5jb20iLCJyb2xlIjoiQ1VTVE9NRVIiLCJpYXQiOjE3MjAwNjYxNzEsImV4cCI6MTcyMDA3MzM3MX0.QqQKPMOE70xDalpUnlycVoVh26KoAVPu5BQp5kLAgig";

        // when
        when(jwtUtil.getTokenWithoutBearer(reqToken)).thenReturn(token);
        when(jwtUtil.isExpired(token)).thenReturn(true);

        // then
        assertThatThrownBy(() -> {
            sut.handle(reqToken);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EXPIRED_ACCESS_TOKEN.getMessage());
    }

    @Test
    @DisplayName("존재하지 않는 유저를 찾을 수 없다.")
    void test3() {

        // given
        String reqToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFAYS5jb20iLCJyb2xlIjoiQ1VTVE9NRVIiLCJpYXQiOjE3MjAwNjYxNzEsImV4cCI6MTcyMDA3MzM3MX0.QqQKPMOE70xDalpUnlycVoVh26KoAVPu5BQp5kLAgig";
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFAYS5jb20iLCJyb2xlIjoiQ1VTVE9NRVIiLCJpYXQiOjE3MjAwNjYxNzEsImV4cCI6MTcyMDA3MzM3MX0.QqQKPMOE70xDalpUnlycVoVh26KoAVPu5BQp5kLAgig";

        // when
        when(jwtUtil.getTokenWithoutBearer(reqToken)).thenReturn(token);
        when(jwtUtil.isExpired(token)).thenReturn(false);
        when(jwtUtil.getEmail(token)).thenReturn("bb@bb.com");

        // then
        assertThatThrownBy(() -> {
            sut.handle(reqToken);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(NOT_EXIST_USER.getMessage());
    }
}