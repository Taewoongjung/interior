package com.interior.application.unittest.command.user.handlers;

import static com.interior.adapter.common.exception.ErrorType.INVALID_SIGNUP_REQUEST_DUPLICATE_TEL;
import static com.interior.helper.config.RedisTestContainerConfig.redisTemplate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.interior.abstraction.serviceutill.IThirdPartyValidationCheckSender;
import com.interior.adapter.inbound.user.webdto.ValidationType;
import com.interior.application.command.user.commands.SendPhoneValidationSmsCommand;
import com.interior.application.command.user.handlers.SendPhoneValidationSmsCommandHandler;
import com.interior.domain.user.repository.UserRepository;
import com.interior.helper.config.RedisTestContainerConfig;
import com.interior.helper.spy.ThirdPartyValidationCheckSenderSpy;
import com.interior.helper.spy.UserRepositorySpy;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.ValueOperations;

@ExtendWith(RedisTestContainerConfig.class)
@DisplayName("SendPhoneValidationSmsCommandHandler 는 ")
class SendPhoneValidationSmsCommandHandlerTest {

    private final IThirdPartyValidationCheckSender smsThirdPartyValidationCheckSender = new ThirdPartyValidationCheckSenderSpy();
    private final UserRepository userRepository = new UserRepositorySpy();
    private final ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);

    private final SendPhoneValidationSmsCommandHandler sut = new SendPhoneValidationSmsCommandHandler(
            smsThirdPartyValidationCheckSender, userRepository, eventPublisher);


    @Test
    @DisplayName("회원가입을 위한 이메일 검증 sms를 보낸다.")
    void test1() throws Exception {

        // given
        String targetPhoneNumber = "01088887777";
        ValidationType validationType = ValidationType.SIGN_UP;

        SendPhoneValidationSmsCommand event = new SendPhoneValidationSmsCommand(targetPhoneNumber,
                validationType);

        ValueOperations<String, Map<String, String>> cache = redisTemplate.opsForValue();

        // when
        sut.handle(event);

        // then
        assertThat(cache.get(targetPhoneNumber)).isNotNull();

        assertThat(Objects.requireNonNull(cache.get(targetPhoneNumber)).get("isVerified"))
                .isEqualTo("false");

        assertThat(Objects.requireNonNull(cache.get(targetPhoneNumber)).get("number"))
                .isEqualTo("123456");
    }

    @Test
    @DisplayName("이미 존재하는 전화번호이면 에러가 발생한다.")
    void test2() {

        // given
        String targetPhoneNumber = "01012345678";
        ValidationType validationType = ValidationType.SIGN_UP;

        SendPhoneValidationSmsCommand event = new SendPhoneValidationSmsCommand(targetPhoneNumber,
                validationType);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(Exception.class)
                .hasMessage(INVALID_SIGNUP_REQUEST_DUPLICATE_TEL.getMessage());
    }
}