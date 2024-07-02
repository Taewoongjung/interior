package com.interior.application.unittest.command.business.handlers;

import static company.CompanyFixture.COMPANY_1;
import static company.CompanyFixture.COMPANY_2;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.interior.adapter.inbound.business.webdto.CreateBusinessWebDtoV1;
import com.interior.adapter.inbound.business.webdto.CreateBusinessWebDtoV1.Req;
import com.interior.application.command.business.commands.CreateBusinessCommand;
import com.interior.application.command.business.handlers.CreateBusinessCommandHandler;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.company.Company;
import com.interior.domain.user.User;
import com.interior.domain.user.UserRole;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

class CreateBusinessCommandHandlerTest {

    private final BusinessRepository businessRepository = mock(BusinessRepository.class);
    private final ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);

    private final CreateBusinessCommandHandler sut = new CreateBusinessCommandHandler(
            businessRepository, eventPublisher);


    @Test
    @DisplayName("사업을 생성할 수 있다.")
    void test1() {

        // given
        List<Company> companyList = new ArrayList<>();
        companyList.add(COMPANY_1);

        User USER = User.of(
                1L,
                "홍길동",
                "aipooh8882@naver.com",
                "12edqw",
                "01088257754",
                UserRole.ADMIN,
                LocalDateTime.now(),
                LocalDateTime.now(),
                companyList);

        CreateBusinessWebDtoV1.Req webReq = new Req(
                "무지개 아파트 사업건",
                "01000",
                "군자로 43",
                "809호",
                "0132104912908451209"
        );

        CreateBusinessCommand event = new CreateBusinessCommand(
                1L,
                webReq,
                USER
        );

        // when
        // then
        sut.handle(event);
    }

    @Test
    @DisplayName("사업을 생성 할 사업체를 찾을 수 없으면 사업을 생성할 수 없다.")
    void test2() {

        // given
        List<Company> companyList = new ArrayList<>();
        companyList.add(COMPANY_2);

        User USER = User.of(
                1L,
                "홍길동",
                "aipooh8882@naver.com",
                "12edqw",
                "01088257754",
                UserRole.ADMIN,
                LocalDateTime.now(),
                LocalDateTime.now(),
                companyList);

        CreateBusinessWebDtoV1.Req webReq = new Req(
                "무지개 아파트 사업건",
                "01000",
                "군자로 43",
                "809호",
                "0132104912908451209"
        );

        CreateBusinessCommand event = new CreateBusinessCommand(
                1L,
                webReq,
                USER
        );

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("사업체를 찾을 수 없습니다.");
    }
}