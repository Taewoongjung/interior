package com.interior.application.readmodel.business.handlers;

import static java.util.stream.Collectors.groupingBy;

import com.interior.abstraction.domain.IQueryHandler;
import com.interior.adapter.inbound.business.webdto.GetBusiness;
import com.interior.application.readmodel.business.queries.GetBusinessQuery;
import com.interior.domain.business.Business;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.business.repository.BusinessRepository;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetBusinessQueryHandler implements
        IQueryHandler<GetBusinessQuery, GetBusiness.Response> {

    private final BusinessRepository businessRepository;

    @Override
    public boolean isQueryHandler() {
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public GetBusiness.Response handle(final GetBusinessQuery query) {
        log.info("process GetBusinessQuery {}", query);

        Business business = businessRepository.findById(query.businessId());

        int count = 0;

        HashMap<String, List<BusinessMaterial>> businessMaterials = new HashMap<>();

        if (business.getBusinessMaterialList() != null) {

            businessMaterials =
                    (HashMap<String, List<BusinessMaterial>>)
                            business.getBusinessMaterialList().stream()
                                    .collect(groupingBy(BusinessMaterial::getUsageCategory));

            count = business.getBusinessMaterialList().size();
        }

        return new GetBusiness.Response(business.getName(), business.getCreatedAt(),
                businessMaterials, count, business.getBusinessProgressList());
    }
}
