package com.interior.application.command.business.commands;

import com.interior.abstraction.domain.ICommand;
import java.util.List;

public record ReviseUsageCategoryOfMaterialCommand(Long businessId,
                                                   List<Long> targetList,
                                                   String usageCategoryName)
        implements ICommand {

}
