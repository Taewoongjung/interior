package com.interior.application.command.business.commands;

import com.interior.abstraction.domain.ICommand;
import com.interior.domain.user.User;

public record ReviseBusinessCommand(Long companyId,
                                    Long businessId,
                                    String changeBusinessName,
                                    User user)
        implements ICommand {

}
