package com.interior.application.command.business;

import static com.interior.adapter.common.exception.ErrorType.NOT_CONTAIN_MATERIAL_IN_THE_BUSINESS;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS_MATERIAL;
import static com.interior.util.CheckUtil.check;

import com.interior.adapter.inbound.business.webdto.CreateBusinessWebDtoV1;
import com.interior.adapter.inbound.business.webdto.ReviseBusinessMaterialWebDtoV1;
import com.interior.adapter.outbound.alarm.dto.event.NewBusinessAlarm;
import com.interior.adapter.outbound.jpa.repository.business.dto.ReviseBusinessMaterial;
import com.interior.application.command.business.dto.CreateBusinessServiceDto.CreateBusinessMaterialDto;
import com.interior.application.command.business.dto.ReviseBusinessServiceDto;
import com.interior.application.command.log.business.dto.event.BusinessDeleteLogEvent;
import com.interior.application.command.log.business.dto.event.BusinessReviseLogEvent;
import com.interior.application.command.log.business.material.dto.event.BusinessMaterialCreateLogEvent;
import com.interior.application.command.log.business.material.dto.event.BusinessMaterialDeleteLogEvent;
import com.interior.application.command.log.business.material.dto.event.BusinessReviseMaterialLogEvent;
import com.interior.domain.business.Business;
import com.interior.domain.business.log.BusinessChangeFieldType;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.business.material.log.BusinessMaterialChangeFieldType;
import com.interior.domain.business.progress.ProgressType;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.business.repository.dto.CreateBusiness;
import com.interior.domain.business.repository.dto.CreateBusinessMaterial;
import com.interior.domain.company.Company;
import com.interior.domain.company.repository.CompanyRepository;
import com.interior.domain.user.User;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessCommandService {

    private final CompanyRepository companyRepository;
    private final BusinessRepository businessRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(rollbackFor = Exception.class)
    public Long createBusiness(
            final Long companyId,
            final CreateBusinessWebDtoV1.Req req,
            final User user
    ) {

        final Long createdBusinessId = businessRepository.save(
                new CreateBusiness(
                        req.businessName(),
                        companyId,
                        null,
                        req.zipCode(),
                        req.mainAddress(),
                        req.subAddress(),
                        req.bdgNumber()
                )
        );

        String companyName = user.getCompanyList()
                .stream()
                .filter(f -> companyId.equals(f.getId()))
                .findFirst().map(Company::getName)
                .orElseThrow(() -> new NoSuchElementException("사업체를 찾을 수 없습니다."));

        String businessAddress =
                "[" + req.zipCode() + "] " + req.mainAddress() + " " + req.subAddress();

        // 새로운 사업 생성 시 알람 발송
        eventPublisher.publishEvent(
                new NewBusinessAlarm(req.businessName(),
                        companyName,
                        user.getName(),
                        user.getEmail(),
                        user.getTel(),
                        businessAddress,
                        req.bdgNumber()
                )
        );

        return createdBusinessId;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean createBusinessMaterial(
            final Long businessId,
            final CreateBusinessMaterialDto req,
            final User user
    ) {

        // 재료 생성
        BusinessMaterial businessMaterial = businessRepository.save(
                new CreateBusinessMaterial(
                        businessId,
                        req.name(),
                        req.usageCategory(),
                        req.category(),
                        req.amount(),
                        req.unit(),
                        req.memo(),
                        req.materialCostPerUnit(),
                        req.laborCostPerUnit()
                ));

        if (businessMaterial != null) {

            // 재료 생성에 대한 로그
            eventPublisher.publishEvent(
                    new BusinessMaterialCreateLogEvent(
                            businessId,
                            businessMaterial.getId(),
                            user.getId(),
                            req.name()));
        }

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBusinessMaterial(
            final Long businessId,
            final Long materialId,
            final User user
    ) {

        if (businessRepository.deleteBusinessMaterial(businessId, materialId)) {

            // 사업 재료 삭제 로그
            eventPublisher.publishEvent(new BusinessMaterialDeleteLogEvent(businessId, materialId,
                    user.getId()));
        }

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBusiness(final Long companyId, final Long businessId, final User user) {

        Business business = businessRepository.findById(businessId);

        if (businessRepository.deleteBusiness(companyId, businessId)) {

            // 사업 삭제 로그
            eventPublisher.publishEvent(
                    new BusinessDeleteLogEvent(businessId, user.getId(), business.getName()));
        }

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean reviseBusiness(
            final Long companyId,
            final Long businessId,
            final ReviseBusinessServiceDto.Req req,
            final User user
    ) {

        Company company = companyRepository.findById(companyId);

        company.validateDuplicateName(req.changeBusinessName());

        Business business = businessRepository.findById(businessId);

        if (businessRepository.reviseBusiness(companyId, businessId, req)) {

            // 사업명 수정 로그
            eventPublisher.publishEvent(
                    new BusinessReviseLogEvent(
                            businessId,
                            user.getId(),
                            BusinessChangeFieldType.REVISE_BUSINESS_NAME,
                            business.getName(),
                            req.changeBusinessName()
                    )
            );
        }

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean reviseUsageCategoryOfMaterial(
            final Long businessId,
            final List<Long> targetList,
            final String usageCategoryName
    ) {

        return businessRepository.reviseUsageCategoryOfMaterial(
                businessId, targetList, usageCategoryName);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean reviseMaterial(
            final Long businessId,
            final Long materialId,
            final ReviseBusinessMaterialWebDtoV1.Req req,
            final Long updaterId
    ) {

        Business business = businessRepository.findById(businessId);

        BusinessMaterial businessMaterial = business.getBusinessMaterialList().stream()
                .filter(f -> materialId.equals(f.getId()))
                .findFirst()
                .orElseThrow(
                        () -> new NoSuchElementException(NOT_EXIST_BUSINESS_MATERIAL.getMessage()));

        check(business.getBusinessMaterialList().stream()
                        .noneMatch(e -> materialId.equals(e.getId())),
                NOT_CONTAIN_MATERIAL_IN_THE_BUSINESS);

        ReviseBusinessMaterial repositoryDto = ReviseBusinessMaterial.of(req, businessMaterial);

        boolean isSuccessRevising = businessRepository.reviseBusinessMaterial(materialId,
                repositoryDto);

        // 사업명 수정 로그
        if (isSuccessRevising) {

            if (repositoryDto.getMaterialName() != null) {
                eventPublisher.publishEvent(
                        new BusinessReviseMaterialLogEvent(businessId, materialId, updaterId,
                                BusinessMaterialChangeFieldType.MATERIAL_NAME,
                                businessMaterial.getName(), req.materialName()));
            }

            if (repositoryDto.getMaterialCategory() != null) {
                eventPublisher.publishEvent(
                        new BusinessReviseMaterialLogEvent(businessId, materialId, updaterId,
                                BusinessMaterialChangeFieldType.MATERIAL_CATEGORY,
                                businessMaterial.getCategory(), req.materialCategory()));
            }

            if (repositoryDto.getMaterialAmount() != null) {
                eventPublisher.publishEvent(
                        new BusinessReviseMaterialLogEvent(businessId, materialId, updaterId,
                                BusinessMaterialChangeFieldType.MATERIAL_AMOUNT,
                                businessMaterial.getAmount().setScale(0).toPlainString(),
                                req.materialAmount().toPlainString()));
            }

            if (repositoryDto.getMaterialAmountUnit() != null) {
                eventPublisher.publishEvent(
                        new BusinessReviseMaterialLogEvent(businessId, materialId, updaterId,
                                BusinessMaterialChangeFieldType.MATERIAL_UNIT,
                                businessMaterial.getUnit(),
                                req.materialAmountUnit()));
            }

            if (repositoryDto.getMaterialMemo() != null) {
                eventPublisher.publishEvent(
                        new BusinessReviseMaterialLogEvent(businessId, materialId, updaterId,
                                BusinessMaterialChangeFieldType.MATERIAL_MEMO,
                                businessMaterial.getMemo(), req.materialMemo()));
            }

            if (repositoryDto.getMaterialCostPerUnit() != null) {
                eventPublisher.publishEvent(
                        new BusinessReviseMaterialLogEvent(businessId, materialId, updaterId,
                                BusinessMaterialChangeFieldType.MATERIAL_COST_PER_UNIT,
                                businessMaterial.getBusinessMaterialExpense() != null ?
                                        businessMaterial.getBusinessMaterialExpense()
                                                .getMaterialCostPerUnit() : null,
                                req.materialCostPerUnit()));
            }

            if (repositoryDto.getLaborCostPerUnit() != null) {
                eventPublisher.publishEvent(
                        new BusinessReviseMaterialLogEvent(businessId, materialId, updaterId,
                                BusinessMaterialChangeFieldType.MATERIAL_LABOR_COST_PER_UNIT,
                                businessMaterial.getBusinessMaterialExpense() != null ?
                                        businessMaterial.getBusinessMaterialExpense()
                                                .getLaborCostPerUnit() : null,
                                req.laborCostPerUnit()));
            }
        }

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateBusinessProgress(final Long businessId, final ProgressType progressType) {
        businessRepository.updateBusinessProgress(businessId, progressType);
    }
}
