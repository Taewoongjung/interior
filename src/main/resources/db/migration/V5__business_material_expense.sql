CREATE TABLE business_material_expense
(
    id                     BIGINT(20) UNSIGNED                      NOT NULL AUTO_INCREMENT,
    business_material_id   BIGINT(20) UNSIGNED                      NOT NULL comment '해당 되는 사업 재료의 id',
    material_cost_per_unit VARCHAR(100)                                 NULL comment '재료비 단가',
    labor_cost_per_unit    VARCHAR(100)                                 NULL comment '노무비 단가',
    created_at             DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    last_modified          DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '사업 재료에 각각에 대한 비용 정보';
