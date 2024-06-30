package com.interior.application.command.user.commands;

import com.interior.abstraction.domain.ICommand;

public record SendPhoneValidationSmsCommand(String targetPhoneNumber) implements ICommand {

}
