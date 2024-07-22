CREATE TABLE business_schedule_alarm
(
    id                         BIGINT(20) UNSIGNED                      NOT NULL AUTO_INCREMENT,
    business_schedule_id       BIGINT(20) UNSIGNED                      NOT NULL COMMENT '연관 사업 스케줄 id',
    alarm_start_date           DATETIME(6)                              NOT NULL COMMENT '스케줄 시작 날짜',
    is_success                 CHAR(1)                                  NOT NULL COMMENT '알람 성공 여부 (T/F)',
    is_deleted                 CHAR(1)                                  NOT NULL COMMENT '알람 삭제 여부 (T/F)',
    deleted_at                 DATETIME(6)                                  NULL COMMENT '해당 알람이 삭제 된 날짜/시간',
    created_at                 DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    last_modified              DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '스케줄 정보 에 연관 된 알람 정보 테이블';

CREATE INDEX `idx-business_schedule_alarm-business_id`
    ON business_schedule_alarm (business_schedule_id);