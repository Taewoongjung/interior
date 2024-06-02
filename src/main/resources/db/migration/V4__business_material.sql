CREATE TABLE business_material
(
    id             BIGINT(20) UNSIGNED          NOT NULL AUTO_INCREMENT,
    business_id    BIGINT(20) UNSIGNED          NOT NULL COMMENT '해당 되는 사업의 id',
    name           VARCHAR(20)                  NOT NULL COMMENT '사업에 사용 될 재료명',
    usage_category VARCHAR(50)   DEFAULT ''     NOT NULL COMMENT '재료 사용 분류',
    category       VARCHAR(50)                  NOT NULL COMMENT '재료의 카테고리',
    amount         DECIMAL(7, 2) DEFAULT 0.00   NOT NULL COMMENT '수량',
    unit           VARCHAR(10)   DEFAULT ''     NOT NULL COMMENT '재료 수량에 대한 단위',
    memo           VARCHAR(500)                     NULL COMMENT '재료에 대한 메모',
    is_deleted     VARCHAR(1)    DEFAULT 'F'    NOT NULL COMMENT '재료 삭제 여부 (F: 삭제 안됨, T: 삭제)',
    created_at     datetime(6)   DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    last_modified  datetime(6)   DEFAULT CURRENT_TIMESTAMP(6) NOT NULL ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '사업 정보';

CREATE INDEX `idx-business_material-business_id`
    ON business_material (business_id);
