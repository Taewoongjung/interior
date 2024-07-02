package com.interior.application.unittest.command.business.handlers;

import static business.material.BusinessMaterialFixture.BM_2;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS_MATERIAL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.interior.adapter.inbound.business.webdto.ReviseBusinessMaterialWebDtoV1;
import com.interior.application.command.business.commands.ReviseMaterialCommand;
import com.interior.application.command.business.handlers.ReviseMaterialCommandHandler;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.helper.spy.BusinessRepositorySpy;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

class ReviseMaterialCommandHandlerTest {

    private final BusinessRepository businessRepository = new BusinessRepositorySpy();
    private final ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);

    private final ReviseMaterialCommandHandler sut = new ReviseMaterialCommandHandler(
            businessRepository, eventPublisher);


    @Test
    @DisplayName("사업 재료를 수정할 수 있다.")
    void test1() {

        // given
        ReviseBusinessMaterialWebDtoV1.Req webReq = new ReviseBusinessMaterialWebDtoV1.Req(
                "재료명", "카테고리", BigDecimal.valueOf(1),
                "수량", "메모",
                "770700", "153000"
        );

        ReviseMaterialCommand event = new ReviseMaterialCommand(
                1L, 36L, webReq, 519L
        );

        // when
        // then

        boolean actual = sut.handle(event);

        assertThat(actual).isTrue();
        assertThat(BM_2.getId()).isEqualTo(36L);
        assertThat(BM_2.getName()).isEqualTo("재료명");
        assertThat(BM_2.getCategory()).isEqualTo("카테고리");
        assertThat(BM_2.getAmount()).isEqualTo(BigDecimal.valueOf(1));
        assertThat(BM_2.getUnit()).isEqualTo("수량");
        assertThat(BM_2.getMemo()).isEqualTo("메모");
        assertThat(BM_2.getBusinessMaterialExpense().getMaterialCostPerUnit()).isEqualTo("770700");
        assertThat(BM_2.getBusinessMaterialExpense().getLaborCostPerUnit()).isEqualTo("153000");
    }

    @Test
    @DisplayName("존재하지 않는 사업 재료는 수정할 수 없다.")
    void test2() {

        // given
        ReviseBusinessMaterialWebDtoV1.Req webReq = new ReviseBusinessMaterialWebDtoV1.Req(
                "재료명", "카테고리", BigDecimal.valueOf(1),
                "수량", "메모",
                "770700", "153000"
        );

        ReviseMaterialCommand event = new ReviseMaterialCommand(
                70L, 36L, webReq, 519L
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
    @DisplayName("삭제 된 사업 재료는 수정할 수 없다.")
    void test3() {

        // given
        ReviseBusinessMaterialWebDtoV1.Req webReq = new ReviseBusinessMaterialWebDtoV1.Req(
                "재료명", "카테고리", BigDecimal.valueOf(1),
                "수량", "메모",
                "770700", "153000"
        );

        ReviseMaterialCommand event = new ReviseMaterialCommand(
                1L, 81L, webReq, 519L
        );

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_EXIST_BUSINESS_MATERIAL.getMessage());
    }

}