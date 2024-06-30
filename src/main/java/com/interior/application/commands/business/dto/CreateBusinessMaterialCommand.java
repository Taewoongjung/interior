package com.interior.application.commands.business.dto;

import com.interior.abstraction.domain.ICommand;
import com.interior.domain.user.User;
import java.math.BigDecimal;

public record CreateBusinessMaterialCommand(Long businessId,
                                            String materialName,
                                            String materialUsageCategory,
                                            String materialCategory,
                                            BigDecimal materialAmount,
                                            String unitOfMaterialAmount,
                                            String materialMemo,
                                            String materialCostPerUnit,
                                            String laborCostPerUnit,
                                            User user)
        implements ICommand {

}
