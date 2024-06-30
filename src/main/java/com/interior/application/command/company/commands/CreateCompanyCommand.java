package com.interior.application.command.company.commands;

import com.interior.abstraction.domain.ICommand;
import com.interior.domain.user.User;

public record CreateCompanyCommand(User user,
                                   String companyName,
                                   String zipCode,
                                   String mainAddress,
                                   String subAddress,
                                   String bdgNumber,
                                   String tel)
        implements ICommand {

}
