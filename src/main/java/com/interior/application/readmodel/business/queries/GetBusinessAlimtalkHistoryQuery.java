package com.interior.application.readmodel.business.queries;

import com.interior.abstraction.domain.IQuery;
import com.interior.domain.business.progress.ProgressType;

public record GetBusinessAlimtalkHistoryQuery(Long businessId, ProgressType progressType) implements IQuery {

}
