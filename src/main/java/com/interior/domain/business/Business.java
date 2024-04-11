package com.interior.domain.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.interior.domain.business.businessmaterial.BusinessMaterial;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Business {

    private Long id;

    private String name;

    private Long hostId;

    private Long customerId;

    private String status;

    private List<BusinessMaterial> businessMaterialList;

    private LocalDateTime lastModified;
    private LocalDateTime createdAt;

    private Business(
            final Long id,
            final String name,
            final Long hostId,
            final Long customerId,
            final String status,
            final List<BusinessMaterial> businessMaterialList,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.hostId = hostId;
        this.customerId = customerId;
        this.status = status;
        this.businessMaterialList = businessMaterialList;
        this.lastModified = lastModified;
        this.createdAt = createdAt;
    }

    public static Business of(
            final String name,
            final Long hostId,
            final Long customerId,
            final String status,
            final List<BusinessMaterial> businessMaterialList
    ) {
        return new Business(null, name, hostId, customerId, status, businessMaterialList,
                LocalDateTime.now(), LocalDateTime.now());
    }
}
