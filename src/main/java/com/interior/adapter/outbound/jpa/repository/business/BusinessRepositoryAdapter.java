package com.interior.adapter.outbound.jpa.repository.business;

import static com.interior.util.converter.jpa.business.BusinessEntityConverter.businessMaterialToEntity;

import com.interior.adapter.outbound.jpa.entity.business.BusinessEntity;
import com.interior.domain.business.Business;
import com.interior.domain.business.businessmaterial.BusinessMaterial;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.business.repository.dto.CreateBusiness;
import com.interior.domain.business.repository.dto.CreateBusinessMaterial;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BusinessRepositoryAdapter implements BusinessRepository {

    private final BusinessJpaRepository businessJpaRepository;
    private final BusinessMaterialJpaRepository businessMaterialJpaRepository;

    @Override
    public Business findById(Long businessId) {

        BusinessEntity business = businessJpaRepository.findById(businessId)
                .orElseThrow(() -> new NoSuchElementException("Business not found"));

        return business.toPojo();
    }

    @Override
    public List<Business> findBusinessByCompanyId(final Long companyId) {

        List<BusinessEntity> business = businessJpaRepository.findBusinessesEntityByCompanyId(companyId);

        return business.stream()
                .map(BusinessEntity::toPojo)
                .collect(Collectors.toList());
    }

    @Override
    public boolean save(final CreateBusiness createBusiness) {

        businessJpaRepository.save(BusinessEntity.of(
                createBusiness.businessName(),
                createBusiness.companyId(),
                createBusiness.customerId(),
                createBusiness.status()
        ));

        return true;
    }

    @Override
    public boolean save(final CreateBusinessMaterial createBusinessMaterial) {

        businessMaterialJpaRepository.save(businessMaterialToEntity(BusinessMaterial.of(
                createBusinessMaterial.businessId(),
                createBusinessMaterial.name(),
                createBusinessMaterial.category(),
                createBusinessMaterial.amount(),
                createBusinessMaterial.memo()
                )
        ));

        return true;
    }
}
