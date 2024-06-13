CREATE TABLE business_contract
(
    id                   BIGINT(20) UNSIGNED                      NOT NULL AUTO_INCREMENT,
    company_id           BIGINT(20) UNSIGNED                      NOT NULL comment '사업이 속한 사업체 id',
    business_id          BIGINT(20) UNSIGNED                      NOT NULL comment '사업의 id',
    contract_type        VARCHAR(50)                              NOT NULL comment '사업 계약 상태 값',
    is_agreed            VARCHAR(1)                                   NULL comment '피계약자의 계약 동의 여부 (빈 값은 아직 미응답)',
    user_id              BIGINT(20) UNSIGNED                      NOT NULL comment '피계약자 id',
    is_deleted           VARCHAR(1) DEFAULT 'F'                   NOT NULL comment '계약 삭제 여부 (F: 삭제 안됨, T: 삭제)',
    created_at           DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '사업 계약 정보';

CREATE INDEX `idx-business_contract-company_id`
    ON business_contract (company_id);

CREATE INDEX `idx-business_contract-business_id`
    ON business_contract (business_id);