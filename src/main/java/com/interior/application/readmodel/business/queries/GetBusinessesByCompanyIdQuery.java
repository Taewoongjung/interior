package com.interior.application.readmodel.business.queries;

import com.interior.abstraction.domain.IQuery;
import com.interior.adapter.inbound.business.enumtypes.QueryType;

public record GetBusinessesByCompanyIdQuery(Long companyId,
                                            QueryType queryType)
        implements IQuery {

}
