package com.interior.domain.business.repository;

import com.interior.application.command.business.dto.ReviseBusinessServiceDto;
import com.interior.domain.business.Business;
import com.interior.domain.business.log.BusinessMaterialLog;
import com.interior.domain.business.repository.dto.CreateBusiness;
import com.interior.domain.business.repository.dto.CreateBusinessMaterial;
import java.util.List;

public interface BusinessRepository {

    Business findById(final Long businessId);

    List<Business> findBusinessByCompanyId(final Long companyId);

    Business findBusinessByCompanyIdAndBusinessId(final Long companyId, final Long businessId);

    List<Business> findAllByCompanyIdIn(final List<Long> companyIdList);

    Long save(final CreateBusiness createBusiness);

    boolean save(final CreateBusinessMaterial createBusinessMaterial);

    boolean deleteBusinessMaterial(final Long businessId, final Long materialId);

    boolean deleteBusiness(final Long companyId, final Long businessId);

    boolean reviseBusiness(final Long userId, final Long businessId,
            final ReviseBusinessServiceDto.Req req);

    boolean reviseUsageCategoryOfMaterial(
            final Long businessId,
            final List<Long> targetList,
            final String usageCategoryName);

    boolean createMaterialUpdateLog(final BusinessMaterialLog businessMaterialLog);
}
