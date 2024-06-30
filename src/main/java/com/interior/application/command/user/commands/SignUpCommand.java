package com.interior.application.command.user.commands;

import com.interior.abstraction.domain.ICommand;
import com.interior.domain.user.UserRole;

public record SignUpCommand(String name,
                            String password,
                            String email,
                            String tel,
                            UserRole role)
        implements ICommand {

}
