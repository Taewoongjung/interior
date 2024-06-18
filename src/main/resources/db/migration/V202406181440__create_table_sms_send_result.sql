CREATE TABLE sms_send_result
(
    id                   BIGINT(20) UNSIGNED                      NOT NULL AUTO_INCREMENT,
    is_success           VARCHAR(1)                                   NULL comment 'sms 발송 성공 여부 (F: 발송 실패, T: 발송 성공)',
    from                 VARCHAR(11)                              NOT NULL comment '발신 번호',
    to                   VARCHAR(11)                              NOT NULL comment '발송 번호',
    created_at           DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '사업 계약 정보';
