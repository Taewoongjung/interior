package com.interior.application.commands.business.dto;

import com.interior.abstraction.domain.ICommand;
import com.interior.domain.user.User;

public record DeleteBusinessCommand(Long companyId,
                                    Long businessId,
                                    User user)
        implements ICommand {

}
