package com.interior.application.command.user.commands;

import com.interior.abstraction.domain.ICommand;

public record ResetUserPasswordCommand(String email,
                                       String phoneNumber,
                                       String password)
        implements ICommand {

}
