package com.interior.application.readmodel.company.queries;

import com.interior.abstraction.domain.IQuery;

public record GetCompanyQuery(String userEmail, Long companyId) implements IQuery {

}
