package com.interior.application.readmodel.schedule.queries;

import com.interior.abstraction.domain.IQuery;
import java.util.List;

public record GetBusinessScheduleByBusinessIdQuery(List<Long> businessIdList)
        implements IQuery {

}
