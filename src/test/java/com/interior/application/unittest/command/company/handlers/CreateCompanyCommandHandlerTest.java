package com.interior.application.unittest.command.company.handlers;

import static com.interior.adapter.common.exception.ErrorType.LIMIT_OF_COMPANY_COUNT_IS_FIVE;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_CUSTOMER;
import static company.CompanyFixture.COMPANY_LIST;
import static company.CompanyFixture.COMPANY_LIST_OVER_5;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.application.command.company.commands.CreateCompanyCommand;
import com.interior.application.command.company.handlers.CreateCompanyCommandHandler;
import com.interior.domain.company.repository.CompanyRepository;
import com.interior.domain.user.User;
import com.interior.domain.user.UserRole;
import com.interior.helper.spy.CompanyRepositorySpy;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

class CreateCompanyCommandHandlerTest {

    private final CompanyRepository companyRepository = new CompanyRepositorySpy();
    private final ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);

    private final CreateCompanyCommandHandler sut = new CreateCompanyCommandHandler(
            companyRepository, eventPublisher);

    @Test
    @DisplayName("사업체를 추가할 수 있다.")
    void test1() {

        // given
        final User user = User.of(
                10L,
                "홍길동",
                "a@a.com",
                "asdqeer1r12jiudjd^312&2ews",
                "01012345678",
                UserRole.ADMIN,
                LocalDateTime.of(2024, 5, 19, 23, 30),
                LocalDateTime.of(2024, 5, 19, 23, 30),
                COMPANY_LIST()
        );

        final CreateCompanyCommand event = new CreateCompanyCommand(
                user,
                "TW 주식회사",
                "한남대로",
                "한남대로 12",
                "101동 2202호",
                "4215034022000820008042329",
                "01088257754");

        // when
        // then
        boolean actual = sut.handle(event);

        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("하나의 유저에 사업체가 5개 초과이면 생성할 수 없다.")
    void test2() {

        // given
        final User user = User.of(
                10L,
                "홍길동",
                "a@a.com",
                "asdqeer1r12jiudjd^312&2ews",
                "01012345678",
                UserRole.ADMIN,
                LocalDateTime.of(2024, 5, 19, 23, 30),
                LocalDateTime.of(2024, 5, 19, 23, 30),
                COMPANY_LIST_OVER_5()
        );

        final CreateCompanyCommand event = new CreateCompanyCommand(
                user,
                "TW 주식회사",
                "한남대로",
                "한남대로 12",
                "101동 2202호",
                "4215034022000820008042329",
                "01088257754");

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(LIMIT_OF_COMPANY_COUNT_IS_FIVE.getMessage());
    }

    @Test
    @DisplayName("존재하지 않는 유저에 사업체를 생성할 수 없다.")
    void test3() {

        // given
        final User user = User.of(
                10L,
                "홍길동",
                "aipooh@a.com",
                "asdqeer1r12jiudjd^312&2ews",
                "01012345678",
                UserRole.ADMIN,
                LocalDateTime.of(2024, 5, 19, 23, 30),
                LocalDateTime.of(2024, 5, 19, 23, 30),
                COMPANY_LIST()
        );

        final CreateCompanyCommand event = new CreateCompanyCommand(
                user,
                "TW 주식회사",
                "한남대로",
                "한남대로 12",
                "101동 2202호",
                "4215034022000820008042329",
                "01088257754");

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_EXIST_CUSTOMER.getMessage());
    }
}