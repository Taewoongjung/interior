package com.interior.application.commands.business.dto;

import com.interior.abstraction.domain.ICommand;
import com.interior.domain.business.progress.ProgressType;

public record UpdateBusinessProgressCommand(Long businessId,
                                            ProgressType progressType)
        implements ICommand {

}
