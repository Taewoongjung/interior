package com.interior.application.command.business;

import com.interior.adapter.inbound.business.webdto.CreateBusiness.CreateBusinessReqDto;
import com.interior.application.command.business.dto.CreateBusinessServiceDto.CreateBusinessMaterialDto;
import com.interior.application.command.business.dto.ReviseBusinessServiceDto;
import com.interior.domain.business.log.BusinessMaterialChangeFieldType;
import com.interior.domain.business.log.BusinessMaterialLog;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.business.repository.dto.CreateBusiness;
import com.interior.domain.business.repository.dto.CreateBusinessMaterial;
import com.interior.domain.company.Company;
import com.interior.domain.company.repository.CompanyRepository;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessCommandService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final BusinessRepository businessRepository;

    @Transactional
    public Long createBusiness(final Long companyId, final CreateBusinessReqDto req) {

        return businessRepository.save(
                new CreateBusiness(
                        req.businessName(),
                        companyId,
                        null,
                        "진행중"
                )
        );
    }

    @Transactional
    public boolean createBusinessMaterial(final Long businessId,
            final CreateBusinessMaterialDto req) {

        businessRepository.save(new CreateBusinessMaterial(
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

        return true;
    }

    @Transactional
    public boolean deleteBusinessMaterial(final Long businessId, final Long materialId) {

        return businessRepository.deleteBusinessMaterial(businessId, materialId);
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

    // 재료 업데이트 시 로그 생성
    public boolean createLogOfChangeMaterials(
            final Long businessMaterialId,
            final String changeField,
            final String beforeData,
            final String afterData,
            final Long updaterId
    ) {

        User user = userRepository.findById(updaterId);

        return businessRepository.createMaterialUpdateLog(
                BusinessMaterialLog.of(
                        businessMaterialId,
                        BusinessMaterialChangeFieldType.from(changeField),
                        beforeData,
                        afterData,
                        updaterId,
                        user.getName(),
                        LocalDateTime.now()
                )
        );
    }
}
