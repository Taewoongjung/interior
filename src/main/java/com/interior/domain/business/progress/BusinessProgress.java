package com.interior.domain.business.progress;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.interior.adapter.outbound.jpa.entity.business.progress.ProgressType;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessProgress {

    private Long id;

    private Long businessId;

    private ProgressType progressType;

    private BoolType isDeleted;

    private LocalDateTime createdAt;

    private BusinessProgress(
            final Long id,
            final Long businessId,
            final ProgressType progressType,
            final BoolType isDeleted,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.businessId = businessId;
        this.progressType = progressType;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }

    // 생성
    public static BusinessProgress of(
            final Long businessId,
            final ProgressType progressType,
            final BoolType isDeleted
    ) {

        return new BusinessProgress(null, businessId, progressType, isDeleted, null);
    }

    // 조회
    public static BusinessProgress of(
            final Long id,
            final Long businessId,
            final ProgressType progressType,
            final BoolType isDeleted,
            final LocalDateTime createdAt
    ) {

        return new BusinessProgress(id, businessId, progressType, isDeleted, createdAt);
    }
}
