package com.interior.application.command.business.handlers;

import static com.interior.adapter.common.exception.ErrorType.NOT_CONTAIN_MATERIAL_IN_THE_BUSINESS;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS_MATERIAL;
import static com.interior.util.CheckUtil.check;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.adapter.outbound.jpa.repository.business.dto.ReviseBusinessMaterial;
import com.interior.application.command.business.commands.ReviseMaterialCommand;
import com.interior.application.command.log.business.material.dto.event.BusinessReviseMaterialLogEvent;
import com.interior.domain.business.Business;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.business.material.log.BusinessMaterialChangeFieldType;
import com.interior.domain.business.repository.BusinessRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviseMaterialCommandHandler implements
        ICommandHandler<ReviseMaterialCommand, Boolean> {

    private final BusinessRepository businessRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean handle(final ReviseMaterialCommand command) {
        log.info("execute ReviseMaterialCommand");

        Business business = businessRepository.findById(command.businessId());

        BusinessMaterial businessMaterial = business.getBusinessMaterialList().stream()
                .filter(f -> command.materialId().equals(f.getId()))
                .findFirst()
                .orElseThrow(
                        () -> new NoSuchElementException(NOT_EXIST_BUSINESS_MATERIAL.getMessage()));

        check(business.getBusinessMaterialList().stream()
                        .noneMatch(e -> command.materialId().equals(e.getId())),
                NOT_CONTAIN_MATERIAL_IN_THE_BUSINESS);

        ReviseBusinessMaterial repositoryDto = ReviseBusinessMaterial.of(command.req(),
                businessMaterial);

        boolean isSuccessRevising = businessRepository.reviseBusinessMaterial(command.materialId(),
                repositoryDto);

        // 사업명 수정 로그
        if (isSuccessRevising) {

            if (repositoryDto.getMaterialName() != null) {
                eventPublisher.publishEvent(
                        new BusinessReviseMaterialLogEvent(command.businessId(),
                                command.materialId(), command.updaterId(),
                                BusinessMaterialChangeFieldType.MATERIAL_NAME,
                                businessMaterial.getName(), command.req().materialName()));
            }

            if (repositoryDto.getMaterialCategory() != null) {
                eventPublisher.publishEvent(
                        new BusinessReviseMaterialLogEvent(command.businessId(),
                                command.materialId(), command.updaterId(),
                                BusinessMaterialChangeFieldType.MATERIAL_CATEGORY,
                                businessMaterial.getCategory(), command.req().materialCategory()));
            }

            if (repositoryDto.getMaterialAmount() != null) {
                eventPublisher.publishEvent(
                        new BusinessReviseMaterialLogEvent(command.businessId(),
                                command.materialId(), command.updaterId(),
                                BusinessMaterialChangeFieldType.MATERIAL_AMOUNT,
                                businessMaterial.getAmount().setScale(0).toPlainString(),
                                command.req().materialAmount().toPlainString()));
            }

            if (repositoryDto.getMaterialAmountUnit() != null) {
                eventPublisher.publishEvent(
                        new BusinessReviseMaterialLogEvent(command.businessId(),
                                command.materialId(), command.updaterId(),
                                BusinessMaterialChangeFieldType.MATERIAL_UNIT,
                                businessMaterial.getUnit(),
                                command.req().materialAmountUnit()));
            }

            if (repositoryDto.getMaterialMemo() != null) {
                eventPublisher.publishEvent(
                        new BusinessReviseMaterialLogEvent(command.businessId(),
                                command.materialId(), command.updaterId(),
                                BusinessMaterialChangeFieldType.MATERIAL_MEMO,
                                businessMaterial.getMemo(), command.req().materialMemo()));
            }

            if (repositoryDto.getMaterialCostPerUnit() != null) {
                eventPublisher.publishEvent(
                        new BusinessReviseMaterialLogEvent(command.businessId(),
                                command.materialId(), command.updaterId(),
                                BusinessMaterialChangeFieldType.MATERIAL_COST_PER_UNIT,
                                businessMaterial.getBusinessMaterialExpense() != null ?
                                        businessMaterial.getBusinessMaterialExpense()
                                                .getMaterialCostPerUnit() : null,
                                command.req().materialCostPerUnit()));
            }

            if (repositoryDto.getLaborCostPerUnit() != null) {
                eventPublisher.publishEvent(
                        new BusinessReviseMaterialLogEvent(command.businessId(),
                                command.materialId(), command.updaterId(),
                                BusinessMaterialChangeFieldType.MATERIAL_LABOR_COST_PER_UNIT,
                                businessMaterial.getBusinessMaterialExpense() != null ?
                                        businessMaterial.getBusinessMaterialExpense()
                                                .getLaborCostPerUnit() : null,
                                command.req().laborCostPerUnit()));
            }
        }

        log.info("ReviseMaterialCommand executed successfully");

        return true;
    }
}
