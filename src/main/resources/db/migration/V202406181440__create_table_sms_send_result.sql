CREATE TABLE sms_send_result
(
    id                   BIGINT(20) UNSIGNED                      NOT NULL AUTO_INCREMENT,
    is_success           CHAR(1)                                  NOT NULL comment 'sms 발송 성공 여부 (F: 발송 실패, T: 발송 성공)',
    type                 VARCHAR(10)                              NOT NULL comment '메시지 타입 (sms, 알림톡 ...)',
    sender               VARCHAR(15)                              NOT NULL COMMENT '발신 번호',
    receiver             VARCHAR(15)                              NOT NULL COMMENT '발송 번호',
    platform_type        VARCHAR(20)                              NOT NULL COMMENT '발송 된 메시징 플랫폼',
    result_code          VARCHAR(20)                              NOT NULL COMMENT '발송 결과 코드',
    msg_id               VARCHAR(15)                              NOT NULL COMMENT '메시지의 id',
    created_at           DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT 'sms 발송 정보';
