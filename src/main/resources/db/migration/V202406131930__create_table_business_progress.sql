CREATE TABLE business_progress
(
    id                   BIGINT(20) UNSIGNED                      NOT NULL AUTO_INCREMENT,
    business_id          BIGINT(20) UNSIGNED                      NOT NULL comment '사업의 id',
    progress_type        VARCHAR(50)                              NOT NULL comment '사업 진행 상태 값',
    contract_id          BIGINT(20) UNSIGNED                      NOT NULL comment '계약 id',
    is_deleted           VARCHAR(1) DEFAULT 'F'                   NOT NULL comment '진행 상태 삭제 여부 (F: 삭제 안됨, T: 삭제)',
    created_at           DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '사업 진행 상태 정보';

CREATE INDEX `idx-business_progress-business_id`
    ON business_progress (business_id);

CREATE INDEX `idx-business_progress-contract_id`
    ON business_progress (contract_id);