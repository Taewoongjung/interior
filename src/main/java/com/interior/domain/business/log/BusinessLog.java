package com.interior.domain.business.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BusinessLog {

    private Long id;

    private Long businessId;

    private BusinessChangeFieldType changeField;

    private String beforeData;

    private String afterData;

    private Long updater;

    private String updaterName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public BusinessLog(
            final Long id,
            final Long businessId,
            final BusinessChangeFieldType changeField,
            final String beforeData,
            final String afterData,
            final Long updater,
            final String updaterName,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.businessId = businessId;
        this.changeField = changeField;
        this.beforeData = beforeData;
        this.afterData = afterData;
        this.updater = updater;
        this.updaterName = updaterName;
        this.createdAt = createdAt;
    }

    // 조회 전용
    public static BusinessLog of(
            final Long id,
            final Long businessId,
            final BusinessChangeFieldType changeField,
            final String beforeData,
            final String afterData,
            final Long updaterId,
            final String updaterName,
            final LocalDateTime createdAt
    ) {
        return new BusinessLog(id, businessId, changeField, beforeData, afterData, updaterId,
                updaterName, createdAt);
    }

    // 생성 전용
    public static BusinessLog of(
            final Long businessId,
            final BusinessChangeFieldType changeField,
            final String beforeData,
            final String afterData,
            final Long updaterId,
            final String updaterName,
            final LocalDateTime createdAt
    ) {
        return new BusinessLog(null, businessId, changeField,
                beforeData, afterData, updaterId, updaterName, createdAt);
    }

    public String getChangeDetail() {

        if (changeField.equals(BusinessChangeFieldType.CREATE_NEW_BUSINESS)) {
            return getAfterData();
        }

        if (changeField.equals(BusinessChangeFieldType.DELETE_BUSINESS)) {
            return getBeforeData();
        }

        return getBeforeData() + " → " + getAfterData();
    }
}
