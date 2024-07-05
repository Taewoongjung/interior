package com.interior.application.unittest.command.user.handlers;

import static com.interior.adapter.common.exception.ErrorType.INVALID_SIGNUP_REQUEST_DUPLICATE_EMAIL;
import static com.interior.adapter.common.exception.ErrorType.NOT_VERIFIED_PHONE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.adapter.outbound.cache.redis.sms.CacheSmsValidationRedisRepository;
import com.interior.application.command.user.commands.SignUpCommand;
import com.interior.application.command.user.handlers.SignUpCommandHandler;
import com.interior.domain.user.UserRole;
import com.interior.domain.user.repository.UserRepository;
import com.interior.helper.spy.UserRepositorySpy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@DisplayName("SignUpCommandHandler 는 ")
class SignUpCommandHandlerTest {

    private final UserRepository userRepository = new UserRepositorySpy();
    private final ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);
    private final BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
    private final CacheSmsValidationRedisRepository cacheSmsValidationRedisRepository = mock(
            CacheSmsValidationRedisRepository.class);

    private final SignUpCommandHandler sut = new SignUpCommandHandler(userRepository,
            eventPublisher, bCryptPasswordEncoder, cacheSmsValidationRedisRepository);

    @Test
    @DisplayName("회원가입을 할 수 있다.")
    void test1() {

        // given
        String name = "홍길동";
        String password = "1waqe2@";
        String email = "acs@asa.com";
        String tel = "01011232467";
        UserRole role = UserRole.BUSINESS_MAN;

        SignUpCommand event = new SignUpCommand(name, password, email, tel, role);

        // when
        String encryptedPwd = "asdasd213er12@!dsa4#";
        when(bCryptPasswordEncoder.encode(password)).thenReturn(encryptedPwd);

        when(cacheSmsValidationRedisRepository.getIsVerifiedByKey(tel)).thenReturn(true);

        // then
        String actual = sut.handle(event);

        assertThat(actual).isEqualTo("홍길동");
    }

    @Test
    @DisplayName("이미 존재하는 이메일이면 회원가입을 할 수 없다.")
    void test2() {

        // given
        String name = "홍길동";
        String password = "1waqe2@";
        String email = "a@a.com";
        String tel = "01011232467";
        UserRole role = UserRole.BUSINESS_MAN;

        SignUpCommand event = new SignUpCommand(name, password, email, tel, role);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(INVALID_SIGNUP_REQUEST_DUPLICATE_EMAIL.getMessage());
    }

    @Test
    @DisplayName("휴대폰 인증이 선행 되지 않으면 회원가입을 할 수 없다.")
    void test3() {

        // given
        String name = "홍길동";
        String password = "1waqe2@";
        String email = "acs@asa.com";
        String tel = "01011232467";
        UserRole role = UserRole.BUSINESS_MAN;

        SignUpCommand event = new SignUpCommand(name, password, email, tel, role);

        // when
        String encryptedPwd = "asdasd213er12@!dsa4#";
        when(bCryptPasswordEncoder.encode(password)).thenReturn(encryptedPwd);

        when(cacheSmsValidationRedisRepository.getIsVerifiedByKey(tel)).thenReturn(false);

        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(NOT_VERIFIED_PHONE.getMessage());
    }
}