package com.interior.application.commands.business.dto;

import com.interior.abstraction.domain.ICommand;
import com.interior.domain.user.User;

public record ReviseBusinessCommand(Long companyId,
                                    Long businessId,
                                    ReviseBusinessServiceDto.Req req,
                                    User user)
        implements ICommand {

}
