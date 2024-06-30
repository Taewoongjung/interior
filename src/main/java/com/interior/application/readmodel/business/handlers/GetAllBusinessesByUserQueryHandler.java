package com.interior.application.readmodel.business.handlers;

import com.interior.abstraction.domain.IQueryHandler;
import com.interior.application.readmodel.business.queries.GetAllBusinessesByUserQuery;
import com.interior.domain.business.Business;
import com.interior.domain.business.repository.BusinessRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllBusinessesByUserQueryHandler implements
        IQueryHandler<GetAllBusinessesByUserQuery, List<Business>> {

    private final BusinessRepository businessRepository;

    @Override
    public boolean isQueryHandler() {
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Business> handle(final GetAllBusinessesByUserQuery query) {
        log.info("process GetAllBusinessesByUserQuery {}", query);
        
        return businessRepository.findAllByCompanyIdIn(query.companyIdList());
    }
}
