package com.interior.abstraction.domain;

public interface ICommandHandler<Command, CommandResult> {

    boolean isCommandHandler();

    CommandResult handle(final Command command);

}
