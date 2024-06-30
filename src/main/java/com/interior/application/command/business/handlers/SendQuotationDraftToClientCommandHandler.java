package com.interior.application.command.business.handlers;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.adapter.outbound.alimtalk.dto.SendAlimTalk;
import com.interior.application.command.business.commands.SendQuotationDraftToClientCommand;
import com.interior.application.command.business.commands.UpdateBusinessProgressCommand;
import com.interior.domain.alimtalk.kakaomsgtemplate.KakaoMsgTemplateType;
import com.interior.domain.business.Business;
import com.interior.domain.business.progress.ProgressType;
import com.interior.domain.company.Company;
import com.interior.domain.company.repository.CompanyRepository;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendQuotationDraftToClientCommandHandler implements
        ICommandHandler<SendQuotationDraftToClientCommand, Boolean> {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UpdateBusinessProgressCommandHandler updateBusinessProgressCommandHandler;

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean handle(final SendQuotationDraftToClientCommand command) {

        Business business = updateBusinessProgressCommandHandler.handle(
                new UpdateBusinessProgressCommand(
                        command.businessId(),
                        ProgressType.QUOTATION_REQUESTED)
        );
        Company company = companyRepository.findById(business.getCompanyId());
        User user = userRepository.findById(company.getOwnerId());

        // 알림톡 발송
        eventPublisher.publishEvent(new SendAlimTalk(
                KakaoMsgTemplateType.REQUEST_QUOTATION_DRAFT,
                command.receiverPhoneNumber(),
                null,
                business,
                company,
                user));

        return true;
    }
}
