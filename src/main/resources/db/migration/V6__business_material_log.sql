CREATE TABLE business_material_log
(
    id                   BIGINT(20) UNSIGNED                      NOT NULL AUTO_INCREMENT,
    business_id          BIGINT(20) UNSIGNED                      NOT NULL comment '사업의 id',
    business_material_id BIGINT(20) UNSIGNED                      NOT NULL comment '사업 재료의 id',
    change_field         VARCHAR(20)                              NOT NULL comment '변경 된 값',
    before_data          VARCHAR(100)                                 NULL comment '변경 전 데이터',
    after_data           VARCHAR(100)                                 NULL comment '변경 후 데이터',
    updater              BIGINT(20) UNSIGNED                      NOT NULL comment '수정 한 사람 id',
    updater_name         VARCHAR(100)                             NOT NULL comment '수정 한 사람 이름',
    created_at           DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '사업 재료 변경 로그 정보';

CREATE INDEX `idx-business_material_log-business_material_id`
    ON business_material_log (business_material_id);