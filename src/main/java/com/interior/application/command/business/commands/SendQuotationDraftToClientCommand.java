package com.interior.application.command.business.commands;

import com.interior.abstraction.domain.ICommand;

public record SendQuotationDraftToClientCommand(Long businessId,
                                                String receiverPhoneNumber)
        implements ICommand {

}
