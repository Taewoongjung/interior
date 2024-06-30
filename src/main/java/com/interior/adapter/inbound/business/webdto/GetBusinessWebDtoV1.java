package com.interior.adapter.inbound.business.webdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.business.progress.BusinessProgress;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class GetBusinessWebDtoV1 {

    public record Response(
            String businessName,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
            LocalDateTime businessCreatedAt,
            HashMap<String, List<BusinessMaterial>> businessMaterials,
            int count,
            List<BusinessProgress> businessProgressesList
    ) {

    }
}
