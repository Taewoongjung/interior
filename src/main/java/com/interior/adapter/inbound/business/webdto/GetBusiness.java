package com.interior.adapter.inbound.business.webdto;

import com.interior.domain.business.material.BusinessMaterial;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class GetBusiness {

    public record Response(
            String businessName,
            LocalDateTime businessCreatedAt,
            HashMap<String, List<BusinessMaterial>> businessMaterials,
            int count
    ) {

    }
}
