package com.interior.application.readmodel.user.queries;

import com.interior.abstraction.domain.IQuery;

public record ValidationCheckOfPhoneNumberQuery(String targetPhoneNumber,
                                                String compTargetNumber)
        implements IQuery {

}
