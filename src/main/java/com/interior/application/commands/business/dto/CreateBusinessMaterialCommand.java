package com.interior.application.commands.business.dto;

import com.interior.abstraction.domain.ICommand;
import com.interior.application.commands.business.dto.CreateBusinessServiceDto.CreateBusinessMaterialDto;
import com.interior.domain.user.User;

public record CreateBusinessMaterialCommand(Long businessId,
                                            CreateBusinessMaterialDto req,
                                            User user)
        implements ICommand {

}
