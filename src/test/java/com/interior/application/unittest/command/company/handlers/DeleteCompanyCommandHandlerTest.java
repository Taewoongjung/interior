package com.interior.application.unittest.command.company.handlers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.interior.application.command.company.commands.DeleteCompanyCommand;
import com.interior.application.command.company.handlers.DeleteCompanyCommandHandler;
import com.interior.domain.company.repository.CompanyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeleteCompanyCommandHandlerTest {

    private final CompanyRepository companyRepository = mock(CompanyRepository.class);

    private final DeleteCompanyCommandHandler sut = new DeleteCompanyCommandHandler(
            companyRepository);

    @Test
    @DisplayName("사업체를 삭제할 수 있다.")
    void test1() {

        // given
        DeleteCompanyCommand event = new DeleteCompanyCommand(1L, 20L);

        // when
        when(companyRepository.delete(any(), any())).thenReturn(true);

        // then
        boolean actual = sut.handle(event);

        assertThat(actual).isTrue();
    }
}