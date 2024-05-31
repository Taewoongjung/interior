package com.interior.application.command.business;

import com.interior.adapter.inbound.business.webdto.CreateBusinessWebDtoV1;
import com.interior.adapter.outbound.alarm.AlarmService;
import com.interior.application.command.business.dto.CreateBusinessServiceDto.CreateBusinessMaterialDto;
import com.interior.application.command.business.dto.ReviseBusinessServiceDto;
import com.interior.application.command.log.business.BusinessLogService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessCommandService {

    private final AlarmService alarmService;
    private final CompanyRepository companyRepository;
    private final BusinessRepository businessRepository;
    private final BusinessLogService businessLogService;

    @Transactional
    public Long createBusiness(final Long companyId,
            final CreateBusinessWebDtoV1.Req req,
            final User user) {

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

    @Transactional
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
        businessLogService.createLogForCreatingBusinessMaterial(
                businessId,
                businessMaterial.getId(),
                user.getId(),
                req.name()
        );

        return true;
    }

    @Transactional
    public boolean deleteBusinessMaterial(final Long businessId, final Long materialId,
            final User user) {

        if (businessRepository.deleteBusinessMaterial(businessId, materialId)) {

            businessLogService.createLogForDeletingBusinessMaterial(businessId, materialId,
                    user.getId());
        }

        return true;
    }

    @Transactional
    public boolean deleteBusiness(final Long companyId, final Long businessId) {

        return businessRepository.deleteBusiness(companyId, businessId);
    }

    @Transactional
    public boolean reviseBusiness(
            final Long companyId,
            final Long businessId,
            final ReviseBusinessServiceDto.Req req
    ) {

        Company company = companyRepository.findById(companyId);

        company.validateDuplicateName(req.changeBusinessName());

        return businessRepository.reviseBusiness(companyId, businessId, req);
    }

    @Transactional
    public boolean reviseUsageCategoryOfMaterial(
            final Long businessId,
            final List<Long> targetList,
            final String usageCategoryName
    ) {

        return businessRepository.reviseUsageCategoryOfMaterial(
                businessId, targetList, usageCategoryName);
    }
}
