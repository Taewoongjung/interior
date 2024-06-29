package com.interior.adapter.outbound.jpa.repository.business;

import com.interior.adapter.outbound.jpa.entity.business.businessthirdpartymessage.BusinessThirdPartyMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessThirdPartyMessageJpaRepository extends
        JpaRepository<BusinessThirdPartyMessageEntity, Long> {
    
}
