package com.interior.adapter.outbound.jpa.repository.business;

import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS_MATERIAL;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_COMPANY;
import static com.interior.util.converter.jpa.business.BusinessEntityConverter.businessMaterialLogToEntity;
import static com.interior.util.converter.jpa.business.BusinessEntityConverter.businessMaterialToEntity;

import com.interior.adapter.outbound.jpa.entity.business.BusinessEntity;
import com.interior.adapter.outbound.jpa.entity.business.expense.BusinessMaterialExpenseEntity;
import com.interior.adapter.outbound.jpa.entity.business.material.BusinessMaterialEntity;
import com.interior.adapter.outbound.jpa.entity.business.material.log.BusinessMaterialLogEntity;
import com.interior.adapter.outbound.jpa.entity.company.CompanyEntity;
import com.interior.adapter.outbound.jpa.repository.company.CompanyJpaRepository;
import com.interior.application.command.business.dto.ReviseBusinessServiceDto;
import com.interior.domain.business.Business;
import com.interior.domain.business.BusinessStatus;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.business.material.log.BusinessMaterialLog;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.business.repository.dto.CreateBusiness;
import com.interior.domain.business.repository.dto.CreateBusinessMaterial;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BusinessRepositoryAdapter implements BusinessRepository {

    private final CompanyJpaRepository companyJpaRepository;
    private final BusinessJpaRepository businessJpaRepository;
    private final BusinessMaterialJpaRepository businessMaterialJpaRepository;
    private final BusinessMaterialLogJpaRepository businessMaterialLogJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public Business findById(final Long businessId) {

        BusinessEntity businessEntities = businessJpaRepository.findById(businessId)
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_BUSINESS.getMessage()));

        // 삭제된 재료들 제외
        businessEntities.getOnlyNotDeletedMaterials();

        return businessEntities.toPojoWithRelations();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Business> findBusinessByCompanyId(final Long companyId) {

        List<BusinessEntity> businessEntities = businessJpaRepository.findBusinessesEntityByCompanyId(
                companyId);

        if (businessEntities == null || businessEntities.size() == 0) {
            return new ArrayList<>();
        }

        return businessEntities.stream()
                .map(BusinessEntity::toPojo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Business findBusinessByCompanyIdAndBusinessId(final Long companyId,
            final Long businessId) {

        BusinessEntity businessEntities = businessJpaRepository.findBusinessEntityByCompanyIdAndId(
                companyId, businessId);

        // 삭제된 재료들 제외
        businessEntities.getOnlyNotDeletedMaterials();

        return businessEntities.toPojoWithRelations();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Business> findAllByCompanyIdIn(final List<Long> companyIdList) {

        List<BusinessEntity> businessEntities = businessJpaRepository.findBusinessEntitiesByCompanyIdIn(
                companyIdList);

        if (businessEntities == null || businessEntities.size() == 0) {
            return new ArrayList<>();
        }

        return businessEntities.stream()
                .map(BusinessEntity::toPojoWithRelations)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long save(final CreateBusiness createBusiness) {

        BusinessEntity business = businessJpaRepository.save(BusinessEntity.of(
                createBusiness.businessName(),
                createBusiness.companyId(),
                createBusiness.customerId(),
                BusinessStatus.from(createBusiness.status())
        ));

        return business.getId();
    }

    @Override
    @Transactional
    public BusinessMaterial save(final CreateBusinessMaterial createBusinessMaterial) {

        BusinessMaterialEntity businessMaterialEntity = businessMaterialJpaRepository.save(
                businessMaterialToEntity(BusinessMaterial.of(
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

        return businessMaterialEntity.toPojo();
    }

    @Override
    @Transactional
    public boolean deleteBusinessMaterial(final Long businessId, final Long materialId) {

        BusinessEntity business = businessJpaRepository.findById(businessId)
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_BUSINESS.getMessage()));

        business.deleteMaterial(materialId);

        return true;
    }

    @Override
    @Transactional
    public boolean deleteBusiness(final Long companyId, final Long businessId) {

        CompanyEntity company = companyJpaRepository.findById(companyId)
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_COMPANY.getMessage()));

        company.deleteBusiness(businessId);

        return true;
    }

    @Override
    @Transactional
    public boolean reviseBusiness(final Long companyId, final Long businessId,
            final ReviseBusinessServiceDto.Req req) {

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
            final String usageCategoryName) {

        BusinessEntity business = businessJpaRepository.findById(businessId)
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_BUSINESS.getMessage()));

        business.getBusinessMaterialList().stream()
                .filter(f -> targetList.contains(f.getId()))
                .forEach(e -> e.setUsageCategory(usageCategoryName));

        businessJpaRepository.save(business);

        return true;
    }

    @Override
    @Transactional
    public boolean createMaterialUpdateLog(final BusinessMaterialLog businessMaterialLog) {

        businessMaterialLogJpaRepository.save(businessMaterialLogToEntity(businessMaterialLog));

        return true;
    }

    @Override
    public BusinessMaterial findBusinessMaterialByMaterialId(final Long materialId) {

        BusinessMaterialEntity entity = businessMaterialJpaRepository.findById(materialId)
                .orElseThrow(
                        () -> new NoSuchElementException(NOT_EXIST_BUSINESS_MATERIAL.getMessage()));

        return entity.toPojo();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessMaterialLog> findBusinessMaterialLogByBusinessId(Long materialId) {

        List<BusinessMaterialLogEntity> materialLogList = businessMaterialLogJpaRepository.findAllByBusinessId(
                materialId);

        if (materialLogList == null || materialLogList.size() == 0) {
            return new ArrayList<>();
        }

        return materialLogList.stream()
                .map(BusinessMaterialLogEntity::toPojo)
                .collect(Collectors.toList());
    }
}
