package com.interior.application.commands.business.dto;

import com.interior.abstraction.domain.ICommand;
import com.interior.adapter.inbound.business.webdto.CreateBusinessWebDtoV1;
import com.interior.domain.user.User;

public record CreateBusinessCommand(Long companyId,
                                    CreateBusinessWebDtoV1.Req req,
                                    User user)
        implements ICommand {

}
