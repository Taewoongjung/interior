package com.interior.adapter.outbound.jpa.entity.business.progress;

import com.interior.domain.util.BoolType;
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
@Table(name = "business_progress")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessProgressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_id", nullable = false, columnDefinition = "bigint")
    private Long businessId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "progress_type", nullable = false, columnDefinition = "varchar(50)")
    private ProgressType progressType;

    @Column(name = "is_deleted", columnDefinition = "varchar(1)")
    @Enumerated(value = EnumType.STRING)
    private BoolType isDeleted;

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    public BusinessProgressEntity(
            final Long id,
            final Long businessId,
            final ProgressType progressType,
            final BoolType isDeleted
    ) {

        this.id = id;
        this.businessId = businessId;
        this.progressType = progressType;
        this.isDeleted = isDeleted;
        this.createdAt = LocalDateTime.now();
    }

    // 생성
    public static BusinessProgressEntity of(
            final Long businessId,
            final ProgressType progressType,
            final BoolType isDeleted
    ) {

        return new BusinessProgressEntity(null, businessId, progressType, isDeleted);
    }

    // 조회
    public static BusinessProgressEntity of(
            final Long id,
            final Long businessId,
            final ProgressType progressType,
            final BoolType isDeleted
    ) {

        return new BusinessProgressEntity(id, businessId, progressType, isDeleted);
    }
}
