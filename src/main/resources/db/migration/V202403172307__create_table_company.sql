CREATE TABLE `company`
(
    `id`              BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`            VARCHAR(20)         NOT NULL                  COMMENT '사업체 이름',
    `address`         VARCHAR(50)         NOT NULL                  COMMENT '사업체 메인 주소',
    `subAddress`      VARCHAR(50)         NOT NULL                  COMMENT '사업체 서브 주소',
    `buildingNumber`  VARCHAR(50)         NOT NULL                  COMMENT '사업체 주소의 빌딩번호',
    `tel`             VARCHAR(20)         NOT NULL                  COMMENT '사업체 전화번호',
    `created_at`      DATETIME(6)         NOT NULL   DEFAULT CURRENT_TIMESTAMP(6),
    `last_modified`   DATETIME(6)         NOT NULL   DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '사업체 정보';