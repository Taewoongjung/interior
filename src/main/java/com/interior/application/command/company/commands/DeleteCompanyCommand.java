package com.interior.application.command.company.commands;

import com.interior.abstraction.domain.ICommand;

public record DeleteCompanyCommand(Long userId, Long companyId) implements ICommand {

}
