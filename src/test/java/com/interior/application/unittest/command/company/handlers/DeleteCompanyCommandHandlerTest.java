package com.interior.application.unittest.command.company.handlers;

import static com.interior.adapter.common.exception.ErrorType.COMPANY_NOT_EXIST_IN_THE_USER;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_CUSTOMER;
import static company.CompanyFixture.COMPANY_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.application.command.company.commands.DeleteCompanyCommand;
import com.interior.application.command.company.handlers.DeleteCompanyCommandHandler;
import com.interior.domain.company.repository.CompanyRepository;
import com.interior.domain.util.BoolType;
import com.interior.helper.spy.CompanyRepositorySpy;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeleteCompanyCommandHandlerTest {

    private final CompanyRepository companyRepository = new CompanyRepositorySpy();

    private final DeleteCompanyCommandHandler sut = new DeleteCompanyCommandHandler(
            companyRepository);

    @Test
    @DisplayName("사업체를 삭제할 수 있다.")
    void test1() {

        // given
        DeleteCompanyCommand event = new DeleteCompanyCommand(10L, 2L);

        // when
        // then
        boolean actual = sut.handle(event);

        assertThat(actual).isTrue();
        assertThat(COMPANY_2.getIsDeleted()).isEqualTo(BoolType.T);
    }

    @Test
    @DisplayName("존재하지 않는 유저는 사업체를 삭제할 수 없다.")
    void test2() {

        // given
        DeleteCompanyCommand event = new DeleteCompanyCommand(15L, 2L);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_EXIST_CUSTOMER.getMessage());
    }

    @Test
    @DisplayName("해당 유저가 가지고 있는 사업체 중 존재하지 않는 사업체는 삭제할 수 없다.")
    void test3() {

        // given
        DeleteCompanyCommand event = new DeleteCompanyCommand(10L, 9L);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(COMPANY_NOT_EXIST_IN_THE_USER.getMessage());
    }
}