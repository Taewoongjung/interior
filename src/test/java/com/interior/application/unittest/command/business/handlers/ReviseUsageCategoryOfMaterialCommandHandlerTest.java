package com.interior.application.unittest.command.business.handlers;

import static business.material.BusinessMaterialFixture.BM_1;
import static business.material.BusinessMaterialFixture.BM_2;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.interior.application.command.business.commands.ReviseUsageCategoryOfMaterialCommand;
import com.interior.application.command.business.handlers.ReviseUsageCategoryOfMaterialCommandHandler;
import com.interior.application.unittest.spy.BusinessRepositorySpy;
import com.interior.domain.business.repository.BusinessRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

class ReviseUsageCategoryOfMaterialCommandHandlerTest {

    private final BusinessRepository businessRepository = new BusinessRepositorySpy();
    private final ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);

    private final ReviseUsageCategoryOfMaterialCommandHandler sut = new ReviseUsageCategoryOfMaterialCommandHandler(
            businessRepository, eventPublisher);


    /*
     * BusinessMaterialFixture
     * BM_1, BM_2 에 대한 테스트
     * */

    @Test
    @DisplayName("재료 사용 분류를 수정할 수 있다.")
    void test1() {

        // given
        List<Long> targetListOfReviseUsageCategory = new ArrayList<>();
        targetListOfReviseUsageCategory.add(32L);
        targetListOfReviseUsageCategory.add(36L);

        ReviseUsageCategoryOfMaterialCommand event = new ReviseUsageCategoryOfMaterialCommand(
                1L, targetListOfReviseUsageCategory, "재료 사용 분류 수정"
        );

        // when
        // then
        boolean actual = sut.handle(event);

        assertThat(actual).isTrue();
        assertThat(BM_1.getId()).isEqualTo(32L);
        assertThat(BM_1.getUsageCategory()).isEqualTo("재료 사용 분류 수정");
        assertThat(BM_2.getId()).isEqualTo(36L);
        assertThat(BM_2.getUsageCategory()).isEqualTo("재료 사용 분류 수정");
    }

    @Test
    @DisplayName("존재하지 않는 사업에는 재료 사용 분류를 수정할 수 없다.")
    void test2() {

        // given
        List<Long> targetListOfReviseUsageCategory = new ArrayList<>();
        targetListOfReviseUsageCategory.add(32L);
        targetListOfReviseUsageCategory.add(36L);

        ReviseUsageCategoryOfMaterialCommand event = new ReviseUsageCategoryOfMaterialCommand(
                25L, targetListOfReviseUsageCategory, "재료 사용 분류 수정"
        );

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_EXIST_BUSINESS.getMessage());
    }

    @Test
    @DisplayName("삭제 된 사업에는 재료 사용 분류를 수정할 수 없다.")
    void test3() {

        // given
        List<Long> targetListOfReviseUsageCategory = new ArrayList<>();
        targetListOfReviseUsageCategory.add(32L);
        targetListOfReviseUsageCategory.add(36L);

        ReviseUsageCategoryOfMaterialCommand event = new ReviseUsageCategoryOfMaterialCommand(
                25L, targetListOfReviseUsageCategory, "재료 사용 분류 수정"
        );

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_EXIST_BUSINESS.getMessage());
    }

    @Test
    @DisplayName("targetList에 해당 되는 사업 재료만 재료 사용 분류를 수정할 수 있다.")
    void test4() {

        // given
        List<Long> targetListOfReviseUsageCategory = new ArrayList<>();
        targetListOfReviseUsageCategory.add(32L);
        targetListOfReviseUsageCategory.add(67L);

        ReviseUsageCategoryOfMaterialCommand event = new ReviseUsageCategoryOfMaterialCommand(
                1L, targetListOfReviseUsageCategory, "재료 사용 분류 수정222"
        );

        // when
        // then
        boolean actual = sut.handle(event);

        assertThat(actual).isTrue();
        assertThat(BM_1.getId()).isEqualTo(32L);
        assertThat(BM_1.getUsageCategory()).isEqualTo("재료 사용 분류 수정222");
        assertThat(BM_2.getId()).isEqualTo(36L);
        assertThat(BM_2.getUsageCategory()).isNotEqualTo("재료 사용 분류 수정222");
    }
}