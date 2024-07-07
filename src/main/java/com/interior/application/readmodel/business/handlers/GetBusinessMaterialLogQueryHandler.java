package com.interior.application.readmodel.business.handlers;

import com.interior.abstraction.domain.IQueryHandler;
import com.interior.application.readmodel.business.queries.GetBusinessMaterialLogQuery;
import com.interior.domain.business.material.log.BusinessMaterialLog;
import com.interior.domain.business.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetBusinessMaterialLogQueryHandler implements
        IQueryHandler<GetBusinessMaterialLogQuery, List<BusinessMaterialLog>> {

    private final BusinessRepository businessRepository;

    @Override
    public boolean isQueryHandler() {
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessMaterialLog> handle(final GetBusinessMaterialLogQuery query) {
        log.info("process GetBusinessMaterialLogQuery {}", query);

        List<BusinessMaterialLog> businessMaterialLogList = businessRepository.findBusinessMaterialLogByBusinessId(
                query.businessId());

        return businessMaterialLogList.stream()
                .sorted(Comparator.comparing(BusinessMaterialLog::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }
}
