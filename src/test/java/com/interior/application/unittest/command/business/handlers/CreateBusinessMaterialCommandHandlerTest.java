package com.interior.application.unittest.command.business.handlers;

import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.interior.application.command.business.commands.CreateBusinessMaterialCommand;
import com.interior.application.command.business.handlers.CreateBusinessMaterialCommandHandler;
import com.interior.application.unittest.spy.BusinessRepositorySpy;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

class CreateBusinessMaterialCommandHandlerTest {

    private final BusinessRepositorySpy businessRepository = new BusinessRepositorySpy();
    private final ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);

    private final CreateBusinessMaterialCommandHandler sut = new CreateBusinessMaterialCommandHandler(
            businessRepository, eventPublisher);


    @Test
    @DisplayName("사업 재료를 추가할 수 있다.")
    void test1() {
        // given
        CreateBusinessMaterialCommand event = new CreateBusinessMaterialCommand(
                1L, "벽돌", "외벽공사", "벽돌류", BigDecimal.valueOf(1), "ea", "메모메모", "11", "22", null);

        // when
        // then
        boolean actual = sut.handle(event);
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("존재하지 않은 사업에는 재료를 추가할 수 없다.")
    void test2() {
        
        // given
        CreateBusinessMaterialCommand event = new CreateBusinessMaterialCommand(
                5L, "벽돌", "외벽공사", "벽돌류", BigDecimal.valueOf(1), "ea", "메모메모", "11", "22", null);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_EXIST_BUSINESS.getMessage());
    }

    @Test
    @DisplayName("삭제 된 사업에는 재료를 추가할 수 없다.")
    void test3() {
        // given
        CreateBusinessMaterialCommand event = new CreateBusinessMaterialCommand(
                3L, "벽돌", "외벽공사", "벽돌류", BigDecimal.valueOf(1), "ea", "메모메모", "11", "22", null);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_EXIST_BUSINESS.getMessage());
    }
}
