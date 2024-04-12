package com.interior.application.businesss;

import com.interior.adapter.inbound.business.webdto.CreateBusiness.CreateBusinessReqDto;
import com.interior.application.businesss.dto.CreateBusinessServiceDto.CreateBusinessMaterialDto;
import com.interior.domain.business.Business;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.business.repository.dto.CreateBusiness;
import com.interior.domain.business.repository.dto.CreateBusinessMaterial;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepository businessRepository;

    @Transactional(readOnly = true)
    public Business getBusiness(final Long businessId) {
        return businessRepository.findById(businessId);
    }

    @Transactional(readOnly = true)
    public List<Business> getBusinessesByCompanyId(final Long companyId) {
        return businessRepository.findBusinessByCompanyId(companyId);
    }

    @Transactional
    public boolean createBusiness(final Long companyId, final CreateBusinessReqDto req) {

        businessRepository.save(new CreateBusiness(
                req.businessName(),
                companyId,
                null,
                "진행중-계약전"
                )
        );

        return true;
    }

    @Transactional
    public boolean createBusinessMaterial(final Long businessId, final CreateBusinessMaterialDto req) {

        businessRepository.save(new CreateBusinessMaterial(
                businessId,
                req.name(),
                req.category(),
                req.amount(),
                req.memo()
        ));

        return true;
    }
}
