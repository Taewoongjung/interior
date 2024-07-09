package com.interior.application.readmodel.user.queries;

import com.interior.abstraction.domain.IQuery;

public record GetUserEmailQuery(String phoneNumber) implements IQuery {

}
