CREATE TABLE `business`
(
    `id`               BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(20)         NOT NULL                  COMMENT '사업명',
    `host_id`          BIGINT(20) UNSIGNED NOT NULL                  COMMENT '사업주체자 id',
    `customer_id`      BIGINT(20) UNSIGNED     NULL                  COMMENT '사업 참여자 id(고객 id)',
    `status`           VARCHAR(20)         NOT NULL                  COMMENT '해당 사업의 상태(진행중, 완료, 취소, ...)',
    `created_at`       DATETIME(6)         NOT NULL   DEFAULT CURRENT_TIMESTAMP(6),
    `last_modified`    DATETIME(6)         NOT NULL   DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '사업 정보';