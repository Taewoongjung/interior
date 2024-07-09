package com.interior.application.readmodel.business.handlers;

import com.interior.abstraction.domain.IQueryHandler;
import com.interior.application.readmodel.business.queryresponses.GetUsageCategoriesOfBusinessQueryResponse;
import com.interior.domain.business.Business;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.business.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetUsageCategoriesOfBusinessQueryHandler implements IQueryHandler<Long, List<GetUsageCategoriesOfBusinessQueryResponse>> {

    private final BusinessRepository businessRepository;

    @Override
    public boolean isQueryHandler() {
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetUsageCategoriesOfBusinessQueryResponse> handle(final Long businessId) {
        log.info("process GetUsageCategoriesOfBusinessQueryHandler {}", businessId);

        Business business = businessRepository.findById(businessId);

        Map<String, List<BusinessMaterial>> listMapByUsageCategory = business.getBusinessMaterialList().stream()
                .collect(Collectors.groupingBy(BusinessMaterial::getUsageCategory));

        List<GetUsageCategoriesOfBusinessQueryResponse> result = new ArrayList<>();

        for (String key : listMapByUsageCategory.keySet()) {
            result.add(new GetUsageCategoriesOfBusinessQueryResponse(key, listMapByUsageCategory.get(key).size()));
        }

        return result;
    }
}
