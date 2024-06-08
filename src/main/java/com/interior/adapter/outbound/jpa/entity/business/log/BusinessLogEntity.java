package com.interior.adapter.outbound.jpa.entity.business.log;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_CHANGE_FIELD;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_UPDATER_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_UPDATER_NAME;
import static com.interior.util.CheckUtil.require;

import com.interior.domain.business.log.BusinessChangeFieldType;
import com.interior.domain.business.log.BusinessLog;
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
@Table(name = "business_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_id", nullable = false, columnDefinition = "bigint")
    private Long businessId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "change_field", nullable = false, columnDefinition = "varchar(50)")
    private BusinessChangeFieldType changeField;

    private String beforeData;

    private String afterData;

    private Long updater;

    private String updaterName;

    @Column(nullable = false, updatable = false, name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    private BusinessLogEntity(
            final Long id,
            final Long businessId,
            final BusinessChangeFieldType changeField,
            final String beforeData,
            final String afterData,
            final Long updater,
            final String updaterName
    ) {
        this.id = id;
        this.businessId = businessId;
        this.changeField = changeField;
        this.beforeData = beforeData;
        this.afterData = afterData;
        this.updater = updater;
        this.updaterName = updaterName;
        this.createdAt = LocalDateTime.now();
    }

    public static BusinessLogEntity of(
            final Long businessId,
            final BusinessChangeFieldType changeField,
            final String beforeData,
            final String afterData,
            final Long updater,
            final String updaterName
    ) {

        require(o -> businessId == null, businessId, EMPTY_BUSINESS_ID);
        require(o -> changeField == null, changeField, EMPTY_CHANGE_FIELD);
        require(o -> updater == null, updater, EMPTY_UPDATER_ID);
        require(o -> updaterName == null, updaterName, EMPTY_UPDATER_NAME);

        return new BusinessLogEntity(null, businessId, changeField, beforeData, afterData, updater,
                updaterName);
    }

    public BusinessLog toPojo() {
        return BusinessLog.of(
                getBusinessId(),
                getChangeField(),
                getBeforeData(),
                getAfterData(),
                getUpdater(),
                getUpdaterName(),
                getCreatedAt()
        );
    }
}
