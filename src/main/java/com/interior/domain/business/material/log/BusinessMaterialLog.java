package com.interior.domain.business.material.log;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_MATERIAL_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_CHANGE_FIELD;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_UPDATER_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_UPDATER_NAME;
import static com.interior.util.CheckUtil.require;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BusinessMaterialLog {

    private final Long id;

    private final Long businessId;

    private final Long businessMaterialId;

    private final BusinessMaterialChangeFieldType changeField;

    private final String beforeData;

    private final String afterData;

    private final Long updater;

    private final String updaterName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createdAt;

    private BusinessMaterialLog(
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

        require(o -> businessId == null, businessId, EMPTY_BUSINESS_ID);
        require(o -> businessMaterialId == null, businessMaterialId, EMPTY_BUSINESS_MATERIAL_ID);
        require(o -> changeField == null, changeField, EMPTY_CHANGE_FIELD);
        require(o -> updaterId == null, updaterId, EMPTY_UPDATER_ID);
        require(o -> updaterName == null, updaterName, EMPTY_UPDATER_NAME);

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

        require(o -> businessId == null, businessId, EMPTY_BUSINESS_ID);
        require(o -> businessMaterialId == null, businessMaterialId, EMPTY_BUSINESS_MATERIAL_ID);
        require(o -> changeField == null, changeField, EMPTY_CHANGE_FIELD);
        require(o -> updaterId == null, updaterId, EMPTY_UPDATER_ID);
        require(o -> updaterName == null, updaterName, EMPTY_UPDATER_NAME);

        return new BusinessMaterialLog(null, businessId, businessMaterialId, changeField,
                beforeData, afterData, updaterId, updaterName, createdAt);
    }

    public String getChangeDetail() {

        if (changeField.equals(BusinessMaterialChangeFieldType.CREATE_NEW_MATERIAL)) {
            return getAfterData();
        }

        if (changeField.equals(BusinessMaterialChangeFieldType.DELETE_MATERIAL)) {
            return getBeforeData();
        }

        return getBeforeData() + " → " + getAfterData();
    }
}
