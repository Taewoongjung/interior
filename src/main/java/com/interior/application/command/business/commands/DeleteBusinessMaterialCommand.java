package com.interior.application.command.business.commands;

import com.interior.abstraction.domain.ICommand;
import com.interior.domain.user.User;

public record DeleteBusinessMaterialCommand(Long businessId,
                                            Long materialId,
                                            User user)
        implements ICommand {

}
