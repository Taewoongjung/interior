CREATE TABLE users
(
    id            BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    name          VARCHAR(20)         NOT NULL COMMENT '유저 이름',
    email         VARCHAR(50)         NOT NULL COMMENT '유저 이메일',
    password      VARCHAR(100)        NOT NULL COMMENT '유저 비밀번호',
    tel           VARCHAR(20)         NOT NULL COMMENT '유저 전화번호',
    role          VARCHAR(20)         NOT NULL COMMENT '유저 역할(CUSTOMER, ADMIN)',
    created_at    DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    last_modified DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`),
    CONSTRAINT unique_email UNIQUE (email)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '유저 정보' ;
