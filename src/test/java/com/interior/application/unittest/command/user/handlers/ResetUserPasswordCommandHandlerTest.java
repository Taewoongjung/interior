package com.interior.application.unittest.command.user.handlers;

import com.interior.adapter.outbound.cache.redis.sms.CacheSmsValidationRedisRepository;
import com.interior.application.command.user.commands.ResetUserPasswordCommand;
import com.interior.application.command.user.handlers.ResetUserPasswordCommandHandler;
import com.interior.domain.user.repository.UserRepository;
import com.interior.helper.config.RedisTestContainerConfig;
import com.interior.helper.spy.UserRepositorySpy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_USER;
import static com.interior.adapter.common.exception.ErrorType.NOT_VERIFIED_PHONE;
import static com.interior.helper.config.RedisTestContainerConfig.redisTemplate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;

@ExtendWith(RedisTestContainerConfig.class)
@DisplayName("ResetUserPasswordCommandHandler 는 ")
class ResetUserPasswordCommandHandlerTest {

    private final UserRepository userRepository = new UserRepositorySpy();
    private final BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
    private final CacheSmsValidationRedisRepository cacheSmsValidationRedisRepository = new CacheSmsValidationRedisRepository(redisTemplate);

    private final ResetUserPasswordCommandHandler sut = new ResetUserPasswordCommandHandler(userRepository, bCryptPasswordEncoder, cacheSmsValidationRedisRepository);


    @Test
    @DisplayName("유저의 비밀번호를 재설정할 수 있다.")
    void test1() {

        // given
        String email = "ss@sss.com";
        String phoneNumber = "01088257754";
        String password = "asdad1fc@";

        ResetUserPasswordCommand event = new ResetUserPasswordCommand(email, phoneNumber, password);

        // when
        cacheSmsValidationRedisRepository.makeBucketByKey(phoneNumber, 111111);
        cacheSmsValidationRedisRepository.setIsVerifiedByKey(phoneNumber);

        // then
        boolean actual = sut.handle(event);

        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("휴대폰 번호 인증이 선행되지 않으면 비밀번호를 재설정할 수 없다.")
    void test2() {

        // given
        String email = "ss@sss.com";
        String phoneNumber = "01088257754";
        String password = "asdad1fc@";

        ResetUserPasswordCommand event = new ResetUserPasswordCommand(email, phoneNumber, password);

        // when
        cacheSmsValidationRedisRepository.makeBucketByKey(phoneNumber, 111111);

        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(Exception.class)
                .hasMessage(NOT_VERIFIED_PHONE.getMessage());
    }

    @Test
    @DisplayName("유저가 존재하지 않으면 비밀번호를 재설정할 수 없다.")
    void test3() {

        // given
        String email = "notexist@notexist.com";
        String phoneNumber = "01088257754";
        String password = "asdad1fc@";

        ResetUserPasswordCommand event = new ResetUserPasswordCommand(email, phoneNumber, password);

        // when
        cacheSmsValidationRedisRepository.makeBucketByKey(phoneNumber, 111111);
        cacheSmsValidationRedisRepository.setIsVerifiedByKey(phoneNumber);

        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(Exception.class)
                .hasMessage(NOT_EXIST_USER.getMessage());
    }

    @Test
    @DisplayName("email에 해당하는 유저의 휴대폰 번호가 존재하지 않으면 비밀번호를 재설정할 수 없다.")
    void test4() {

        // given
        String email = "ss@sss.com";
        String phoneNumber = "010notexist";
        String password = "asdad1fc@";

        ResetUserPasswordCommand event = new ResetUserPasswordCommand(email, phoneNumber, password);

        // when
        cacheSmsValidationRedisRepository.makeBucketByKey(phoneNumber, 111111);
        cacheSmsValidationRedisRepository.setIsVerifiedByKey(phoneNumber);

        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(Exception.class)
                .hasMessage(NOT_EXIST_USER.getMessage());
    }
}