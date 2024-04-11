CREATE TABLE `business_material`
(
    `id`               BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `business_id`      BIGINT(20) UNSIGNED NOT NULL                  COMMENT '해당 되는 사업의 id',
    `name`             VARCHAR(20)         NOT NULL                  COMMENT '사업에 사용 될 재료명',
    `category`         VARCHAR(50)         NOT NULL                  COMMENT '재료의 카테고리',
    `amount`           INT                 NOT NULL default 0        COMMENT '수량',
    `memo`             VARCHAR(500)            NULL                  COMMENT '재료에 대한 메모',
    `created_at`       DATETIME(6)         NOT NULL   DEFAULT CURRENT_TIMESTAMP(6),
    `last_modified`    DATETIME(6)         NOT NULL   DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '사업 정보';