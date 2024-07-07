package com.interior.application.unittest.command.business.handlers;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.application.command.business.commands.UpdateBusinessProgressCommand;
import com.interior.application.command.business.handlers.UpdateBusinessProgressCommandHandler;
import com.interior.domain.business.Business;
import com.interior.domain.business.progress.ProgressType;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.helper.spy.BusinessRepositorySpy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static com.interior.adapter.common.exception.ErrorType.DUPLICATE_PROGRESS_VALUE;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class UpdateBusinessProgressCommandHandlerTest {

    private final BusinessRepository businessRepository = new BusinessRepositorySpy();

    private final UpdateBusinessProgressCommandHandler sut = new UpdateBusinessProgressCommandHandler(
            businessRepository);


    @Test
    @DisplayName("사업 진행 사항을 업데이트 할 수 있다.")
    void test1() {

        // given
        UpdateBusinessProgressCommand event = new UpdateBusinessProgressCommand(
                1L, ProgressType.IN_PROGRESS);

        // when
        // then
        Business actual = sut.handle(event);

        assertThat(actual.getBusinessProgressList().stream()
                .filter(f -> f.getProgressType().equals(ProgressType.IN_PROGRESS))
                .findFirst().orElse(null)).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 사업의 진행 사항은 업데이트 할 수 없다.")
    void test2() {

        // given
        UpdateBusinessProgressCommand event = new UpdateBusinessProgressCommand(
                24L, ProgressType.IN_PROGRESS);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_EXIST_BUSINESS.getMessage());
    }

    @Test
    @DisplayName("삭제 된 사업의 진행 사항은 업데이트 할 수 없다.")
    void test3() {

        // given
        UpdateBusinessProgressCommand event = new UpdateBusinessProgressCommand(
                24L, ProgressType.IN_PROGRESS);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_EXIST_BUSINESS.getMessage());
    }

    @Test
    @DisplayName("이미 존재하는 진행 상태를 업데이트 할 수 없다.")
    void test4() {

        // given
        UpdateBusinessProgressCommand event = new UpdateBusinessProgressCommand(
                2L, ProgressType.IN_PROGRESS);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(DUPLICATE_PROGRESS_VALUE.getMessage());
    }
}