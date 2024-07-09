package com.interior.application.unittest.command.business.handlers;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.application.command.business.commands.SendQuotationDraftToClientCommand;
import com.interior.application.command.business.commands.UpdateBusinessProgressCommand;
import com.interior.application.command.business.handlers.SendQuotationDraftToClientCommandHandler;
import com.interior.application.command.business.handlers.UpdateBusinessProgressCommandHandler;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.company.repository.CompanyRepository;
import com.interior.domain.user.repository.UserRepository;
import com.interior.helper.spy.BusinessRepositorySpy;
import com.interior.helper.spy.CompanyRepositorySpy;
import com.interior.helper.spy.UserRepositorySpy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.util.NoSuchElementException;

import static com.interior.adapter.common.exception.ErrorType.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("SendQuotationDraftToClientCommandHandler 는 ")
class SendQuotationDraftToClientCommandHandlerTest {

    private final UserRepository userRepository = new UserRepositorySpy();
    private final CompanyRepository companyRepository = new CompanyRepositorySpy();
    private final BusinessRepository businessRepository = new BusinessRepositorySpy();
    private final ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);
    private final UpdateBusinessProgressCommandHandler updateBusinessProgressCommandHandler = mock(UpdateBusinessProgressCommandHandler.class);

    private final SendQuotationDraftToClientCommandHandler sut = new SendQuotationDraftToClientCommandHandler(
            userRepository, companyRepository, businessRepository, eventPublisher, updateBusinessProgressCommandHandler);


    @Test
    @DisplayName("견적서 초안을 보낼 수 있다. (견적서를 보내면 해당 사업의 진행 사항이 QUOTATION_REQUESTED 으로 변경된다)")
    void test1() {

        // given
        Long businessId = 4L;
        String receiverPhoneNumber = "01012345678";

        SendQuotationDraftToClientCommand event = new SendQuotationDraftToClientCommand(businessId, receiverPhoneNumber);

        // when
        when(updateBusinessProgressCommandHandler.handle(any(UpdateBusinessProgressCommand.class))).thenReturn(any());

        // then
        boolean actual = sut.handle(event);

        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("이미 사업의 진행 사항이 QUOTATION_REQUESTED 으로 변경 되었을 때는 변경하지 않고 견적서 초안을 보낸다.")
    void test2() {

        // given
        Long businessId = 4L;
        String receiverPhoneNumber = "01012345678";

        SendQuotationDraftToClientCommand event = new SendQuotationDraftToClientCommand(businessId, receiverPhoneNumber);

        // when
        boolean actual = sut.handle(event);

        // then

        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("사업이 존재하지 않으면 견적서 초안을 보낼 수 없다.")
    void test3() {

        // given
        Long businessId = 12L;
        String receiverPhoneNumber = "01012345678";

        SendQuotationDraftToClientCommand event = new SendQuotationDraftToClientCommand(businessId, receiverPhoneNumber);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_EXIST_BUSINESS.getMessage());
    }

    @Test
    @DisplayName("사업체가 존재하지 않으면 견적서 초안을 보낼 수 없다.")
    void test4() {

        // given
        Long businessId = 21L;
        String receiverPhoneNumber = "01012345678";

        SendQuotationDraftToClientCommand event = new SendQuotationDraftToClientCommand(businessId, receiverPhoneNumber);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_EXIST_COMPANY.getMessage());
    }

    @Test
    @DisplayName("유저가 존재하지 않으면 견적서 초안을 보낼 수 없다.")
    void test5() {

        // given
        Long businessId = 22L;
        String receiverPhoneNumber = "01012345678";

        SendQuotationDraftToClientCommand event = new SendQuotationDraftToClientCommand(businessId, receiverPhoneNumber);

        // when
        // then
        assertThatThrownBy(() -> {
            sut.handle(event);
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(NOT_EXIST_CUSTOMER.getMessage());
    }
}