package com.interior.application.readmodel.user.queries;

import com.interior.abstraction.domain.IQuery;

public record VerifyUserQuery(String email, String phoneNumber) implements IQuery {

}
