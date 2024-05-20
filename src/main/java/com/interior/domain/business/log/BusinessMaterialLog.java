package com.interior.domain.business.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class BusinessMaterialLog {

    private Long id;

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
            final Long businessMaterialId,
            final BusinessMaterialChangeFieldType changeField,
            final String beforeData,
            final String afterData,
            final Long updater,
            final String updaterName,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.businessMaterialId = businessMaterialId;
        this.changeField = changeField;
        this.beforeData = beforeData;
        this.afterData = afterData;
        this.updater = updater;
        this.updaterName = updaterName;
        this.createdAt = createdAt;
    }

    public static BusinessMaterialLog of(
            final Long businessMaterialId,
            final BusinessMaterialChangeFieldType changeField,
            final String beforeData,
            final String afterData,
            final Long updaterId,
            final String updaterName,
            final LocalDateTime createdAt
    ) {
        return new BusinessMaterialLog(null, businessMaterialId, changeField, beforeData, afterData,
                updaterId, updaterName, createdAt);
    }
}
