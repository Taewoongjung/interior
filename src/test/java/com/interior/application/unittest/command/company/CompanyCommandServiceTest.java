package com.interior.application.unittest.command.company;

import static com.interior.adapter.common.exception.ErrorType.LIMIT_OF_COMPANY_COUNT_IS_FIVE;
import static company.CompanyFixture.COMPANY_LIST;
import static company.CompanyFixture.COMPANY_LIST_OVER_5;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.application.command.company.CompanyCommandService;
import com.interior.application.command.company.dto.CreateCompanyServiceDto;
import com.interior.domain.company.Company;
import com.interior.domain.company.repository.CompanyRepository;
import com.interior.domain.user.User;
import com.interior.domain.user.UserRole;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

class CompanyCommandServiceTest {

    private final CompanyRepository companyRepository = mock(CompanyRepository.class);
    private final ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);

    private final CompanyCommandService sut = new CompanyCommandService(companyRepository,
            eventPublisher);

    @Test
    @DisplayName("사업체를 추가할 수 있다.")
    void test1() {

        // given
        final CreateCompanyServiceDto.CreateCompanyDto req = new CreateCompanyServiceDto.CreateCompanyDto(
                "TW 주식회사",
                "한남대로",
                "한남대로 12",
                "101동 2202호",
                "4215034022000820008042329",
                "01088257754"
        );

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

        // when
        when(companyRepository.save(anyString(), any(Company.class))).thenReturn(true);

        // then
        boolean actual = sut.createCompany(user, req);

        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("데이터베이스 작업 에러로 사업체를 추가할 수 없다.")
    void test2() {

        // given
        final CreateCompanyServiceDto.CreateCompanyDto req = new CreateCompanyServiceDto.CreateCompanyDto(
                "TW 주식회사",
                "한남대로",
                "한남대로 12",
                "101동 2202호",
                "4215034022000820008042329",
                "01088257754"
        );

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

        // when
        when(companyRepository.save(anyString(), any(Company.class))).thenReturn(false);

        // then
        boolean actual = sut.createCompany(user, req);

        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("하나의 유저에 사업체가 5개 초과이면 생성할 수 없다.")
    void test3() {

        // given
        final CreateCompanyServiceDto.CreateCompanyDto req = new CreateCompanyServiceDto.CreateCompanyDto(
                "TW 주식회사",
                "한남대로",
                "한남대로 12",
                "101동 2202호",
                "4215034022000820008042329",
                "01088257754"
        );

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

        // when
        // then
        assertThrows(InvalidInputException.class,
                () -> sut.createCompany(user, req),
                LIMIT_OF_COMPANY_COUNT_IS_FIVE.getMessage());
    }
}