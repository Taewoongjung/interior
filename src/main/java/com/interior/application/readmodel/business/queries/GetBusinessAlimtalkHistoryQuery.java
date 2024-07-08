package com.interior.application.readmodel.business.queries;

import com.interior.abstraction.domain.IQuery;
import com.interior.adapter.inbound.business.enumtypes.ProgressQueryType;

public record GetBusinessAlimtalkHistoryQuery(Long businessId,
                                              ProgressQueryType progressType)
        implements IQuery {

}
