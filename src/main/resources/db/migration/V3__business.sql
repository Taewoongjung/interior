CREATE TABLE business
(
    id            BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    name          VARCHAR(20)         NOT NULL COMMENT '사업명',
    company_id    BIGINT(20) UNSIGNED NOT NULL COMMENT '회사 id',
    customer_id   BIGINT(20) UNSIGNED NULL     COMMENT '사업 참여자 id(고객 id)',
    status        VARCHAR(20)         NOT NULL COMMENT '해당 사업의 상태(진행중, 완료, 취소, ...)',
    status_detail VARCHAR(50)         NULL     COMMENT '사업 상태 상세',
    created_at    DATETIME(6) default CURRENT_TIMESTAMP(6) NOT NULL,
    last_modified DATETIME(6) default CURRENT_TIMESTAMP(6) NOT NULL ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '사업 정보';

CREATE INDEX `idx-business-customer_id`
    ON business (customer_id);
