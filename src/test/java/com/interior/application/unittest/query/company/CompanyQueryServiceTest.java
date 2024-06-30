package com.interior.application.unittest.query.company;

import static company.CompanyFixture.COMPANY_LIST;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.interior.application.readmodel.company.CompanyQueryService;
import com.interior.domain.company.Company;
import com.interior.domain.user.User;
import com.interior.domain.user.UserRole;
import com.interior.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CompanyQueryServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);

    private final CompanyQueryService sut = new CompanyQueryService(userRepository);

    @Test
    @DisplayName("사업체 정보를 찾을 수 있다.")
    void test1() {

        // given
        String userEmail = "a@a.com";
        Long companyId = 1L;
        User USER = User.of(
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
        when(userRepository.findByEmail(userEmail)).thenReturn(USER);

        // then
        Company actual = sut.getCompany(userEmail, companyId);
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getName()).isEqualTo("TW");
        assertThat(actual.getZipCode()).isEqualTo("01000");
        assertThat(actual.getOwnerId()).isEqualTo(10L);
        assertThat(actual.getAddress()).isEqualTo("군자로 43");
        assertThat(actual.getSubAddress()).isEqualTo("809호");
        assertThat(actual.getBuildingNumber()).isEqualTo("0132104912908451209");
        assertThat(actual.getTel()).isEqualTo("01012345678");
    }

    @Test
    @DisplayName("파라메터로 요청 받은 companyId 가 userEmail 에 해당하는 유저에 없으면 사업체 정보를 찾을 수 없다.")
    void test2() {

        // given
        String userEmail = "a@a.com";
        Long companyId = 11L;
        User USER = User.of(
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
        when(userRepository.findByEmail(userEmail)).thenReturn(USER);

        // then
        Company actual = sut.getCompany(userEmail, companyId);
        assertThat(actual).isNull();
    }
}