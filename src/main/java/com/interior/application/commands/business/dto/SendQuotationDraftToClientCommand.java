package com.interior.application.commands.business.dto;

import com.interior.abstraction.domain.ICommand;

public record SendQuotationDraftToClientCommand(Long businessId,
                                                String receiverPhoneNumber)
        implements ICommand {

}
