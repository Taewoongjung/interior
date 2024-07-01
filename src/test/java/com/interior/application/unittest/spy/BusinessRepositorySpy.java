package com.interior.application.unittest.spy;

import static business.BusinessFixture.getBusinessList;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS;

import com.interior.adapter.inbound.business.enumtypes.QueryType;
import com.interior.adapter.outbound.jpa.repository.business.dto.ReviseBusinessMaterial;
import com.interior.domain.business.Business;
import com.interior.domain.business.log.BusinessLog;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.business.material.log.BusinessMaterialLog;
import com.interior.domain.business.progress.ProgressType;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.business.repository.dto.CreateBusiness;
import com.interior.domain.business.repository.dto.CreateBusinessMaterial;
import com.interior.domain.util.BoolType;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.transaction.annotation.Transactional;

public class BusinessRepositorySpy implements BusinessRepository {

    @Override
    public Business findById(Long businessId) {
        return null;
    }

    @Override
    public List<Business> findBusinessByCompanyId(Long companyId, QueryType queryType) {
        return null;
    }

    @Override
    public Business findBusinessByCompanyIdAndBusinessId(Long companyId,
            Long businessId) {
        return null;
    }

    @Override
    public List<Business> findAllByCompanyIdIn(List<Long> companyIdList) {
        return null;
    }

    @Override
    public Long save(CreateBusiness createBusiness) {
        return null;
    }

    @Override
    @Transactional
    public BusinessMaterial save(CreateBusinessMaterial createBusinessMaterial) {

        List<Business> businessList = getBusinessList();

        Business business = businessList.stream()
                .filter(f -> createBusinessMaterial.businessId().equals(f.getId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_BUSINESS.getMessage()));

        if (business.getIsDeleted() == BoolType.T) {
            throw new NoSuchElementException(NOT_EXIST_BUSINESS.getMessage());
        }

        /* save */

        return null;
    }

    @Override
    public boolean deleteBusinessMaterial(Long businessId, Long materialId) {
        return false;
    }

    @Override
    public boolean deleteBusiness(Long companyId, Long businessId) {
        return false;
    }

    @Override
    public boolean reviseBusiness(Long userId, Long businessId,
            String changeBusinessName) {
        return false;
    }

    @Override
    public boolean reviseUsageCategoryOfMaterial(Long businessId, List<Long> targetList,
            String usageCategoryName) {
        return false;
    }

    @Override
    public boolean createBusinessMaterialUpdateLog(BusinessMaterialLog businessMaterialLog) {
        return false;
    }

    @Override
    public BusinessMaterial findBusinessMaterialByMaterialId(Long materialId) {
        return null;
    }

    @Override
    public List<BusinessMaterialLog> findBusinessMaterialLogByBusinessId(Long businessId) {
        return null;
    }

    @Override
    public boolean createBusinessUpdateLog(BusinessLog businessLog) {
        return false;
    }

    @Override
    public boolean reviseBusinessMaterial(Long materialId,
            ReviseBusinessMaterial reviseReq) {
        return false;
    }

    @Override
    public Business updateBusinessProgress(Long businessId, ProgressType progressType) {
        return null;
    }
}
