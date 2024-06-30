package com.interior.abstraction.domain;

public interface IQueryHandler<Query, Dto> {

    boolean isQueryHandler();

    Dto handle(final Query query);

}
