package com.interior.application.readmodel.business.queries;

import com.interior.abstraction.domain.IQuery;
import java.util.List;

public record GetAllBusinessesByUserQuery(List<Long> companyIdList) implements IQuery {

}
