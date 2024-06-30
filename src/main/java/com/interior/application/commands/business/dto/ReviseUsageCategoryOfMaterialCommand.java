package com.interior.application.commands.business.dto;

import com.interior.abstraction.domain.ICommand;
import java.util.List;

public record ReviseUsageCategoryOfMaterialCommand(Long businessId,
                                                   List<Long> targetList,
                                                   String usageCategoryName)
        implements ICommand {

}
