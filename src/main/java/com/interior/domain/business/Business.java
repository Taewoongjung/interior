package com.interior.domain.business;

import static com.interior.util.CheckUtil.check;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.interior.adapter.common.exception.ErrorType;
import com.interior.domain.business.material.BusinessMaterial;
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

    private Long companyId;

    private Long customerId;

    private String status;

    private List<BusinessMaterial> businessMaterialList;

    private LocalDateTime lastModified;
    private LocalDateTime createdAt;

    private Business(
            final Long id,
            final String name,
            final Long companyId,
            final Long customerId,
            final String status,
            final List<BusinessMaterial> businessMaterialList,
            final LocalDateTime lastModified,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.customerId = customerId;
        this.status = status;
        this.businessMaterialList = businessMaterialList;
        this.lastModified = lastModified;
        this.createdAt = createdAt;
    }

    public static Business of(
            final Long id,
            final String name,
            final Long companyId,
            final Long customerId,
            final String status,
            final List<BusinessMaterial> businessMaterialList
    ) {
        return new Business(id, name, companyId, customerId, status, businessMaterialList,
                LocalDateTime.now(), LocalDateTime.now());
    }

    public static Business of(
            final String name,
            final Long companyId,
            final Long customerId,
            final String status,
            final List<BusinessMaterial> businessMaterialList
    ) {
        return new Business(null, name, companyId, customerId, status, businessMaterialList,
                LocalDateTime.now(), LocalDateTime.now());
    }

    public void setName(final String name) {
        check(name == null || "".equals(name.trim()), ErrorType.INVALID_BUSINESS_NAME);

        this.name = name;
    }
}
