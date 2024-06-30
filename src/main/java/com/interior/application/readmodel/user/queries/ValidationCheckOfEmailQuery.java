package com.interior.application.readmodel.user.queries;

import com.interior.abstraction.domain.IQuery;

public record ValidationCheckOfEmailQuery(String targetEmail,
                                          String compTargetNumber)
        implements IQuery {

}
