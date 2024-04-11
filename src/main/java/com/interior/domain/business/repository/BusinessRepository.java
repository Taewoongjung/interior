package com.interior.domain.business.repository;

import com.interior.domain.business.Business;
import com.interior.domain.business.repository.dto.CreateBusiness;
import java.util.List;

public interface BusinessRepository {

    Business findById(final Long businessId);

    List<Business> findBusinessByHostId(final Long hostId);

    boolean save(final CreateBusiness createBusiness);
}
