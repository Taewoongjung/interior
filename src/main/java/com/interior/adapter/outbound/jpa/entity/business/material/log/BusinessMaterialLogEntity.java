package com.interior.adapter.outbound.jpa.entity.business.material.log;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_MATERIAL_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_CHANGE_FIELD;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_UPDATER_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_UPDATER_NAME;
import static com.interior.util.CheckUtil.require;

import com.interior.domain.business.log.BusinessMaterialChangeFieldType;
import com.interior.domain.business.log.BusinessMaterialLog;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@ToString
@Table(name = "business_material_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessMaterialLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_material_id", nullable = false, columnDefinition = "bigint")
    private Long businessMaterialId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "change_field", nullable = false, columnDefinition = "varchar(50)")
    private BusinessMaterialChangeFieldType changeField;

    private String beforeData;

    private String afterData;

    private Long updater;

    private String updaterName;

    @Column(nullable = false, updatable = false, name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    private BusinessMaterialLogEntity(
            final Long id,
            final Long businessMaterialId,
            final BusinessMaterialChangeFieldType changeField,
            final String beforeData,
            final String afterData,
            final Long updater,
            final String updaterName
    ) {
        this.id = id;
        this.businessMaterialId = businessMaterialId;
        this.changeField = changeField;
        this.beforeData = beforeData;
        this.afterData = afterData;
        this.updater = updater;
        this.updaterName = updaterName;
        this.createdAt = LocalDateTime.now();
    }

    public static BusinessMaterialLogEntity of(
            final Long businessMaterialId,
            final BusinessMaterialChangeFieldType changeField,
            final String beforeData,
            final String afterData,
            final Long updater,
            final String updaterName
    ) {

        require(o -> businessMaterialId == null, businessMaterialId, EMPTY_BUSINESS_MATERIAL_ID);
        require(o -> changeField == null, changeField, EMPTY_CHANGE_FIELD);
        require(o -> updater == null, updater, EMPTY_UPDATER_ID);
        require(o -> updaterName == null, updaterName, EMPTY_UPDATER_NAME);

        return new BusinessMaterialLogEntity(null, businessMaterialId, changeField, beforeData,
                afterData, updater, updaterName);
    }

    public BusinessMaterialLog toPojo() {
        return BusinessMaterialLog.of(
                getBusinessMaterialId(),
                getChangeField(),
                getBeforeData(),
                getAfterData(),
                getUpdater(),
                getUpdaterName(),
                getCreatedAt()
        );
    }
}
