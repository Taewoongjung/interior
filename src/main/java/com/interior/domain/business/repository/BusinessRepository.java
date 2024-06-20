package com.interior.domain.business.repository;

import com.interior.adapter.inbound.business.enumtypes.QueryType;
import com.interior.adapter.outbound.jpa.repository.business.dto.ReviseBusinessMaterial;
import com.interior.application.command.business.dto.ReviseBusinessServiceDto;
import com.interior.domain.business.Business;
import com.interior.domain.business.log.BusinessLog;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.business.material.log.BusinessMaterialLog;
import com.interior.domain.business.repository.dto.CreateBusiness;
import com.interior.domain.business.repository.dto.CreateBusinessMaterial;

import java.util.List;

public interface BusinessRepository {

    Business findById(final Long businessId);

    List<Business> findBusinessByCompanyId(final Long companyId, final QueryType queryType);

    Business findBusinessByCompanyIdAndBusinessId(final Long companyId, final Long businessId);

    List<Business> findAllByCompanyIdIn(final List<Long> companyIdList);

    Long save(final CreateBusiness createBusiness);

    BusinessMaterial save(final CreateBusinessMaterial createBusinessMaterial);

    boolean deleteBusinessMaterial(final Long businessId, final Long materialId);

    boolean deleteBusiness(final Long companyId, final Long businessId);

    boolean reviseBusiness(final Long userId, final Long businessId,
                           final ReviseBusinessServiceDto.Req req);

    boolean reviseUsageCategoryOfMaterial(
            final Long businessId,
            final List<Long> targetList,
            final String usageCategoryName);

    boolean createBusinessMaterialUpdateLog(final BusinessMaterialLog businessMaterialLog);

    BusinessMaterial findBusinessMaterialByMaterialId(final Long materialId);

    List<BusinessMaterialLog> findBusinessMaterialLogByBusinessId(final Long businessId);

    boolean createBusinessUpdateLog(final BusinessLog businessLog);

    boolean reviseBusinessMaterial(final BusinessMaterial businessMaterial, final ReviseBusinessMaterial reviseReq);
}
