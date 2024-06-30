package com.interior.application.command.business.commands;

import com.interior.abstraction.domain.ICommand;
import com.interior.adapter.inbound.business.webdto.ReviseBusinessMaterialWebDtoV1;

public record ReviseMaterialCommand(Long businessId,
                                    Long materialId,
                                    ReviseBusinessMaterialWebDtoV1.Req req,
                                    Long updaterId)
        implements ICommand {

}
