package com.interior.abstraction.domain;

public interface ICommandHandler<Command, CommandResult> {

    CommandResult handle(final Command command);

    boolean isCommandHandler();
}
