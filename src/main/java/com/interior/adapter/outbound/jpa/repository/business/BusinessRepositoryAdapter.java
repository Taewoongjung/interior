package com.interior.adapter.outbound.jpa.repository.business;

import com.interior.adapter.inbound.business.enumtypes.QueryType;
import com.interior.adapter.outbound.jpa.entity.business.BusinessEntity;
import com.interior.adapter.outbound.jpa.entity.business.expense.BusinessMaterialExpenseEntity;
import com.interior.adapter.outbound.jpa.entity.business.material.BusinessMaterialEntity;
import com.interior.adapter.outbound.jpa.entity.business.material.log.BusinessMaterialLogEntity;
import com.interior.adapter.outbound.jpa.entity.company.CompanyEntity;
import com.interior.adapter.outbound.jpa.repository.business.dto.ReviseBusinessMaterial;
import com.interior.adapter.outbound.jpa.repository.company.CompanyJpaRepository;
import com.interior.application.command.business.dto.ReviseBusinessServiceDto;
import com.interior.domain.business.Business;
import com.interior.domain.business.log.BusinessLog;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.business.material.log.BusinessMaterialLog;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.business.repository.dto.CreateBusiness;
import com.interior.domain.business.repository.dto.CreateBusinessMaterial;
import com.interior.domain.util.BoolType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.interior.adapter.common.exception.ErrorType.*;
import static com.interior.util.CheckUtil.check;
import static com.interior.util.converter.jpa.business.BusinessEntityConverter.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BusinessRepositoryAdapter implements BusinessRepository {

    private final CompanyJpaRepository companyJpaRepository;
    private final BusinessJpaRepository businessJpaRepository;
    private final BusinessLogJpaRepository businessLogJpaRepository;
    private final BusinessMaterialJpaRepository businessMaterialJpaRepository;
    private final BusinessMaterialLogJpaRepository businessMaterialLogJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public Business findById(final Long businessId) {

        BusinessEntity businessEntities = findBusinessById(businessId);

        check(businessEntities.getIsDeleted() == BoolType.T, NOT_EXIST_BUSINESS);

        // 삭제된 재료들 제외
        businessEntities.getOnlyNotDeletedMaterials();

        return businessEntities.toPojoWithRelations();
    }

    private BusinessEntity findBusinessById(final Long id) {

        return businessJpaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_BUSINESS.getMessage()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Business> findBusinessByCompanyId(final Long companyId, final QueryType queryType) {

        List<BusinessEntity> businessEntities = businessJpaRepository.findBusinessesEntityByCompanyId(
                companyId);

        if (businessEntities == null || businessEntities.size() == 0) {
            return new ArrayList<>();
        }

        if (queryType != null) {
            // queryType 이 "사업관리" 면 businessList 도 함께 조회
            if ("사업관리".equals(queryType.getType())) {
                return businessEntities.stream()
                        .filter(f -> f.getIsDeleted() == BoolType.F)
                        .map(BusinessEntity::toPojoWithRelations)
                        .collect(Collectors.toList());
            }
        }

        return businessEntities.stream()
                .filter(f -> f.getIsDeleted() == BoolType.F)
                .map(BusinessEntity::toPojo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Business findBusinessByCompanyIdAndBusinessId(final Long companyId,
                                                         final Long businessId) {

        BusinessEntity businessEntities = businessJpaRepository.findBusinessEntityByCompanyIdAndId(
                companyId, businessId);

        check(businessEntities.getIsDeleted() == BoolType.T, NOT_EXIST_BUSINESS);

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
                .filter(f -> f.getIsDeleted() == BoolType.F)
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
                createBusiness.zipCode(),
                createBusiness.mainAddress(),
                createBusiness.subAddress(),
                createBusiness.bdgNumber()
        ));

        business.setInitialProgress();

        return business.getId();
    }

    @Override
    @Transactional
    public BusinessMaterial save(final CreateBusinessMaterial createBusinessMaterial) {

        BusinessEntity businessEntities = findBusinessById(createBusinessMaterial.businessId());
        businessEntities.setStartingMakingQuotationProgress();

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
    public boolean createBusinessMaterialUpdateLog(final BusinessMaterialLog businessMaterialLog) {

        businessMaterialLogJpaRepository.save(businessMaterialLogToEntity(businessMaterialLog));

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public BusinessMaterial findBusinessMaterialByMaterialId(final Long materialId) {

        BusinessMaterialEntity entity = businessMaterialJpaRepository.findById(materialId)
                .orElseThrow(
                        () -> new NoSuchElementException(NOT_EXIST_BUSINESS_MATERIAL.getMessage()));

        return entity.toPojo();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessMaterialLog> findBusinessMaterialLogByBusinessId(final Long materialId) {

        List<BusinessMaterialLogEntity> materialLogList = businessMaterialLogJpaRepository.findAllByBusinessId(
                materialId);

        if (materialLogList == null || materialLogList.size() == 0) {
            return new ArrayList<>();
        }

        return materialLogList.stream()
                .map(BusinessMaterialLogEntity::toPojo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean createBusinessUpdateLog(final BusinessLog businessLog) {

        businessLogJpaRepository.save(businessLogToEntity(businessLog));

        return true;
    }

    @Override
    @Transactional
    public boolean reviseBusinessMaterial(final BusinessMaterial businessMaterial, final ReviseBusinessMaterial reviseReq) {

        BusinessMaterialEntity businessMaterialEntity = businessMaterialToEntity(businessMaterial);

        if (reviseReq.getMaterialName() != null) {
            businessMaterialEntity.setBusinessMaterialName(reviseReq.getMaterialName());
        }

        if (reviseReq.getMaterialCategory() != null) {
            businessMaterialEntity.setCategory(reviseReq.getMaterialCategory());
        }

        if (reviseReq.getMaterialAmount() != null) {
            businessMaterialEntity.setAmount(reviseReq.getMaterialAmount());
        }

        if (reviseReq.getMaterialAmountUnit() != null) {
            businessMaterialEntity.setUnit(reviseReq.getMaterialAmountUnit());
        }

        if (reviseReq.getMaterialMemo() != null) {
            businessMaterialEntity.setMemo(reviseReq.getMaterialMemo());
        }

        if (businessMaterialEntity.getBusinessMaterialExpense() != null) {

        } else { // 기존에 비용 정보가 없었던 재료는 새로 생성

            String materialCostPerUnit = null;
            String laborCostPerUnit = null;

            if (reviseReq.getMaterialCostPerUnit() != null) {
                materialCostPerUnit = reviseReq.getMaterialCostPerUnit();
            }

            if (reviseReq.getLaborCostPerUnit() != null) {
                laborCostPerUnit = reviseReq.getLaborCostPerUnit();
            }

            businessMaterialEntity.setBusinessMaterialExpense(
                    BusinessMaterialExpenseEntity.of(
                            businessMaterialEntity.getBusinessId(),
                            materialCostPerUnit,
                            laborCostPerUnit
                    )
            );
        }

        businessMaterialJpaRepository.save(businessMaterialEntity);

        return true;
    }
}
