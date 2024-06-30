package com.interior.application.readmodel.business.queries;

import com.interior.abstraction.domain.IQuery;

public record GetExcelProgressInfoQuery(String taskId) implements IQuery {

}
