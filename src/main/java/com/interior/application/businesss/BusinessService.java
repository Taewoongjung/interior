package com.interior.application.businesss;

import com.interior.adapter.inbound.business.webdto.CreateBusiness.CreateBusinessReqDto;
import com.interior.domain.business.Business;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.business.repository.dto.CreateBusiness;
import com.interior.domain.user.User;
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
    public List<Business> getBusinessesByHostId(final Long hostId) {
        return businessRepository.findBusinessByHostId(hostId);
    }

    public boolean createBusiness(final User user, final CreateBusinessReqDto req) {

        businessRepository.save(new CreateBusiness(
                req.businessName(),
                user.getId(),
                null,
                "진행중"
                )
        );

        return true;
    }
}
