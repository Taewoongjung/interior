package com.interior.adapter.outbound.jpa.repository.business;

import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_COMPANY;
import static com.interior.util.converter.jpa.business.BusinessEntityConverter.businessMaterialToEntity;

import com.interior.adapter.outbound.jpa.entity.business.BusinessEntity;
import com.interior.adapter.outbound.jpa.entity.business.expense.BusinessMaterialExpenseEntity;
import com.interior.adapter.outbound.jpa.entity.business.material.BusinessMaterialEntity;
import com.interior.adapter.outbound.jpa.entity.company.CompanyEntity;
import com.interior.adapter.outbound.jpa.repository.company.CompanyJpaRepository;
import com.interior.application.businesss.dto.ReviseBusinessServiceDto;
import com.interior.domain.business.Business;
import com.interior.domain.business.expense.BusinessMaterialExpense;
import com.interior.domain.business.material.BusinessMaterial;
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

    private final CompanyJpaRepository companyJpaRepository;
    private final BusinessJpaRepository businessJpaRepository;
    private final BusinessMaterialJpaRepository businessMaterialJpaRepository;

    @Override
    public Business findById(Long businessId) {

        BusinessEntity business = businessJpaRepository.findById(businessId)
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_BUSINESS.getMessage()));

        return business.toPojo();
    }

    @Override
    public List<Business> findBusinessByCompanyId(final Long companyId) {

        List<BusinessEntity> businessEntities = businessJpaRepository.findBusinessesEntityByCompanyId(companyId);

        return businessEntities.stream()
                .map(BusinessEntity::toPojo)
                .collect(Collectors.toList());
    }

    @Override
    public Business findBusinessByCompanyIdAndBusinessId(final Long companyId,
            final Long businessId) {

        BusinessEntity businessEntities = businessJpaRepository.findBusinessEntityByCompanyIdAndId(companyId, businessId);

        return businessEntities.toPojo();
    }

    @Override
    public List<Business> findAllByCompanyIdIn(final List<Long> companyIdList) {

        List<BusinessEntity> businessEntities = businessJpaRepository.findBusinessEntitiesByCompanyIdIn(
                companyIdList);

        return businessEntities.stream()
                .map(BusinessEntity::toPojo)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(final CreateBusiness createBusiness) {

        BusinessEntity business = businessJpaRepository.save(BusinessEntity.of(
                createBusiness.businessName(),
                createBusiness.companyId(),
                createBusiness.customerId(),
                createBusiness.status()
        ));

        return business.getId();
    }

    @Override
    public boolean save(final CreateBusinessMaterial createBusinessMaterial) {

        BusinessMaterialEntity businessMaterialEntity = businessMaterialJpaRepository.save(businessMaterialToEntity(BusinessMaterial.of(
                createBusinessMaterial.businessId(),
                createBusinessMaterial.name(),
                createBusinessMaterial.usageCategory(),
                createBusinessMaterial.category(),
                createBusinessMaterial.amount(),
                createBusinessMaterial.unit(),
                createBusinessMaterial.memo()
            )
        ));

        businessMaterialEntity.setBusinessMaterialExpense(
                BusinessMaterialExpenseEntity.of(
                    businessMaterialEntity.getId(),
                    createBusinessMaterial.materialCostPerUnit(),
                    createBusinessMaterial.laborCostPerUnit()
            )
        );

        businessMaterialJpaRepository.save(businessMaterialEntity);

        return true;
    }

    @Override
    public boolean deleteBusinessMaterial(final Long businessId, final Long materialId) {

        BusinessEntity business = businessJpaRepository.findById(businessId)
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_BUSINESS.getMessage()));

        business.deleteMaterial(materialId);

        return true;
    }

    @Override
    public boolean deleteBusiness(final Long companyId, final Long businessId) {

        CompanyEntity company = companyJpaRepository.findById(companyId)
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_COMPANY.getMessage()));

        company.deleteBusiness(businessId);

        return true;
    }

    @Override
    public boolean reviseBusiness(final Long companyId, final Long businessId, final ReviseBusinessServiceDto.Req req) {

        CompanyEntity company = companyJpaRepository.findById(companyId)
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_COMPANY.getMessage()));

        company.reviseBusiness(businessId, req);

        companyJpaRepository.save(company);

        return true;
    }

    @Override
    public boolean reviseUsageCategoryOfMaterial(
            final Long businessId,
            final List<Long> targetList,
            final String usageCategoryName)
    {

        BusinessEntity business = businessJpaRepository.findById(businessId)
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_BUSINESS.getMessage()));

        business.getBusinessMaterialList().stream()
                .filter(f -> targetList.contains(f.getId()))
                .forEach(e -> e.setUsageCategory(usageCategoryName));

        businessJpaRepository.save(business);

        return true;
    }
}
