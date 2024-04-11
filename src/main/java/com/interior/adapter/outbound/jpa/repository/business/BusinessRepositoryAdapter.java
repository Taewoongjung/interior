package com.interior.adapter.outbound.jpa.repository.business;

import com.interior.adapter.outbound.jpa.entity.business.BusinessEntity;
import com.interior.domain.business.Business;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.business.repository.dto.CreateBusiness;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BusinessRepositoryAdapter implements BusinessRepository {

    private final BusinessJpaRepository businessJpaRepository;

    @Override
    public Business findById(Long businessId) {
        BusinessEntity business = businessJpaRepository.findById(businessId)
                .orElseThrow(() -> new NoSuchElementException("Business not found"));
        return business.toPojo();
    }

    @Override
    public List<Business> findBusinessByHostId(final Long hostId) {
        List<BusinessEntity> business = businessJpaRepository.findBusinessesEntityByHostId(hostId);

        return business.stream()
                .map(BusinessEntity::toPojo)
                .collect(Collectors.toList());
    }

    @Override
    public boolean save(final CreateBusiness createBusiness) {

        businessJpaRepository.save(BusinessEntity.of(
                createBusiness.businessName(),
                createBusiness.hostId(),
                createBusiness.customerId(),
                createBusiness.status()
        ));

        return true;
    }
}
