package com.interior.application.command.business;

import com.interior.adapter.inbound.business.webdto.CreateBusinessWebDtoV1;
import com.interior.adapter.outbound.alarm.AlarmService;
import com.interior.application.command.business.dto.CreateBusinessServiceDto.CreateBusinessMaterialDto;
import com.interior.application.command.business.dto.ReviseBusinessServiceDto;
import com.interior.application.command.log.business.material.BusinessMaterialLogService;
import com.interior.application.command.log.business.material.dto.event.BusinessDeletedEvent;
import com.interior.application.command.log.business.material.dto.event.BusinessReviseEvent;
import com.interior.domain.business.Business;
import com.interior.domain.business.material.BusinessMaterial;
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
@Transactional(rollbackFor = Exception.class)
public class BusinessCommandService {

    private final AlarmService alarmService;
    private final CompanyRepository companyRepository;
    private final BusinessRepository businessRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final BusinessMaterialLogService businessMaterialLogService;

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
                        "생성됨"
                )
        );

        String companyName = user.getCompanyList()
                .stream()
                .filter(f -> companyId.equals(f.getId()))
                .findFirst().map(Company::getName)
                .orElseThrow(() -> new NoSuchElementException("사업체를 찾을 수 없습니다."));

        alarmService.sendNewBusinessAlarm(
                req.businessName(),
                companyName,
                user.getName(),
                user.getEmail(),
                user.getTel()
        );

        return createdBusinessId;
    }

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

        // 재료 생성에 대한 로그
        businessMaterialLogService.createLogForCreatingBusinessMaterial(
                businessId,
                businessMaterial.getId(),
                user.getId(),
                req.name()
        );

        return true;
    }

    public boolean deleteBusinessMaterial(
            final Long businessId,
            final Long materialId,
            final User user
    ) {

        if (businessRepository.deleteBusinessMaterial(businessId, materialId)) {

            businessMaterialLogService.createLogForDeletingBusinessMaterial(businessId, materialId,
                    user.getId());
        }

        return true;
    }

    public boolean deleteBusiness(final Long companyId, final Long businessId, final User user) {

        Business business = businessRepository.findById(businessId);

        businessRepository.deleteBusiness(companyId, businessId);

        // 사업 삭제 로그
        eventPublisher.publishEvent(
                new BusinessDeletedEvent(businessId, user.getId(), business.getName()));

        return true;
    }

    public boolean reviseBusiness(
            final Long companyId,
            final Long businessId,
            final ReviseBusinessServiceDto.Req req,
            final User user
    ) {

        Company company = companyRepository.findById(companyId);

        company.validateDuplicateName(req.changeBusinessName());

        Business business = businessRepository.findById(businessId);

        businessRepository.reviseBusiness(companyId, businessId, req);

        // 사업명 수정 로그
        eventPublisher.publishEvent(
                new BusinessReviseEvent(businessId, user.getId(), business.getName(),
                        req.changeBusinessName()));

        return true;
    }

    public boolean reviseUsageCategoryOfMaterial(
            final Long businessId,
            final List<Long> targetList,
            final String usageCategoryName
    ) {

        return businessRepository.reviseUsageCategoryOfMaterial(
                businessId, targetList, usageCategoryName);
    }
}
