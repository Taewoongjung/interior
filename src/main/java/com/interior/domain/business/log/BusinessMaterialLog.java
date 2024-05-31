package com.interior.domain.business.log;

import static com.interior.domain.business.log.BusinessMaterialChangeFieldType.CREATE_NEW_MATERIAL;
import static com.interior.domain.business.log.BusinessMaterialChangeFieldType.DELETE_NEW_MATERIAL;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BusinessMaterialLog {

    private Long id;

    private Long businessId;

    private Long businessMaterialId;

    private BusinessMaterialChangeFieldType changeField;

    private String beforeData;

    private String afterData;

    private Long updater;

    private String updaterName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public BusinessMaterialLog(
            final Long id,
            final Long businessId,
            final Long businessMaterialId,
            final BusinessMaterialChangeFieldType changeField,
            final String beforeData,
            final String afterData,
            final Long updater,
            final String updaterName,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.businessId = businessId;
        this.businessMaterialId = businessMaterialId;
        this.changeField = changeField;
        this.beforeData = beforeData;
        this.afterData = afterData;
        this.updater = updater;
        this.updaterName = updaterName;
        this.createdAt = createdAt;
    }

    // 조회 전용
    public static BusinessMaterialLog of(
            final Long id,
            final Long businessId,
            final Long businessMaterialId,
            final BusinessMaterialChangeFieldType changeField,
            final String beforeData,
            final String afterData,
            final Long updaterId,
            final String updaterName,
            final LocalDateTime createdAt
    ) {
        return new BusinessMaterialLog(id, businessId, businessMaterialId, changeField, beforeData,
                afterData, updaterId, updaterName, createdAt);
    }

    // 생성 전용
    public static BusinessMaterialLog of(
            final Long businessId,
            final Long businessMaterialId,
            final BusinessMaterialChangeFieldType changeField,
            final String beforeData,
            final String afterData,
            final Long updaterId,
            final String updaterName,
            final LocalDateTime createdAt
    ) {
        return new BusinessMaterialLog(null, businessId, businessMaterialId, changeField,
                beforeData, afterData, updaterId, updaterName, createdAt);
    }

    public String getChangeDetail() {

        if (changeField.equals(CREATE_NEW_MATERIAL)) {
            return getAfterData() + " (생성)";
        }

        if (changeField.equals(DELETE_NEW_MATERIAL)) {
            return getBeforeData() + " (삭제)";
        }

        return getBeforeData() + " → " + getAfterData();
    }
}
