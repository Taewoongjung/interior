CREATE TABLE company
(
    id              BIGINT(20) UNSIGNED      NOT NULL AUTO_INCREMENT,
    name            VARCHAR(20)              NOT NULL COMMENT '사업체 이름',
    zipcode         VARCHAR(5)  DEFAULT '0'  NOT NULL COMMENT '회사의 우편번호',
    owner_id        BIGINT(20) UNSIGNED      NOT NULL COMMENT '회수 소유자의 id (userId)',
    address         VARCHAR(50)              NOT NULL COMMENT '사업체 메인 주소',
    sub_address     VARCHAR(50)              NOT NULL COMMENT '사업체 부주소',
    building_number VARCHAR(50)              NOT NULL COMMENT '사업체 주소의 빌딩번호',
    tel             VARCHAR(20)              NOT NULL COMMENT '사업체 전화번호',
    created_at      DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    last_modified   DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '사업체 정보'
