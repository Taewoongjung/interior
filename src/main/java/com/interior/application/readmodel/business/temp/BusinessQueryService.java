package com.interior.application.readmodel.business.temp;

import com.interior.domain.business.material.log.BusinessMaterialLog;
import com.interior.domain.business.repository.BusinessRepository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessQueryService {

    private final BusinessRepository businessRepository;


    @Transactional(readOnly = true)
    public List<BusinessMaterialLog> getBusinessMaterialLog(final Long businessId) {

        List<BusinessMaterialLog> businessMaterialLogList = businessRepository.findBusinessMaterialLogByBusinessId(
                businessId);

        return businessMaterialLogList.stream()
                .sorted(Comparator.comparing(BusinessMaterialLog::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }
}
