package com.interior.abstraction.domain;

public interface IQueryHandler<Query, Dto> {

    Dto handle(final Query query);

    boolean isQueryHandler();
}
