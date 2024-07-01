package com.interior.application.unittest.command.business.handlers;

import static com.interior.adapter.common.exception.ErrorType.INAPPROPRIATE_REQUEST;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS_MATERIAL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.application.command.business.commands.DeleteBusinessMaterialCommand;
import com.interior.application.command.business.handlers.DeleteBusinessMaterialCommandHandler;
import com.interior.application.unittest.spy.BusinessRepositorySpy;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.user.User;
import com.interior.domain.user.UserRole;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

class DeleteBusinessMaterialCommandHandlerTest {

    private final BusinessRepository businessRepository = new BusinessRepositorySpy();
    private final ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);

    private final DeleteBusinessMaterialCommandHandler sut = new DeleteBusinessMaterialCommandHandler(
            businessRepository, eventPublisher);

    private User USER = User.of(
            1L,
            "홍길동",
            "aipooh8882@naver.com",
            "12edqw",
            "01088257754",
            UserRole.ADMIN,
            LocalDateTime.now(),
            LocalDateTime.now(),
            null);

    @Test
    @DisplayName("사업 재료를 삭제할 수 있다.")
    void test1() {

        // given
        DeleteBusinessMaterialCommand event = new DeleteBusinessMaterialCommand(1L, 36L, USER);

        // when
        // then
        boolean actual = sut.handle(event);

        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 사업의 재료는 삭제할 수 없다.")
    void test2() {

        // given
        DeleteBusinessMaterialCommand event = new DeleteBusinessMaterialCommand(20L, 36L, USER);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_EXIST_BUSINESS.getMessage());
    }

    @Test
    @DisplayName("materialId가 없으면 재료를 삭제할 수 없다.")
    void test3() {

        // given
        DeleteBusinessMaterialCommand event = new DeleteBusinessMaterialCommand(1L, null, USER);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(INAPPROPRIATE_REQUEST.getMessage());
    }

    @Test
    @DisplayName("존재하지 않는 재료는 삭제할 수 없다.")
    void test4() {

        // given
        DeleteBusinessMaterialCommand event = new DeleteBusinessMaterialCommand(1L, 16L, USER);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_EXIST_BUSINESS_MATERIAL.getMessage());
    }

    @Test
    @DisplayName("이미 삭제 된 재료는 삭제할 수 없다.")
    void test5() {

        // given
        DeleteBusinessMaterialCommand event = new DeleteBusinessMaterialCommand(1L, 32L, USER);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_EXIST_BUSINESS_MATERIAL.getMessage());
    }
}