package com.interior.application.command.user.commands;

import com.interior.abstraction.domain.ICommand;
import com.interior.adapter.inbound.user.webdto.ValidationType;

public record SendPhoneValidationSmsCommand(String targetPhoneNumber,
                                            ValidationType validationType)
        implements ICommand {

}
