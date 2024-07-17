CREATE TABLE business_schedule
(
    id                         BIGINT(20) UNSIGNED                      NOT NULL AUTO_INCREMENT,
    business_id                BIGINT(20) UNSIGNED                      NOT NULL COMMENT '연관 사업 id',
    user_id                    BIGINT(20) UNSIGNED                      NOT NULL COMMENT '일정을 등록한 사람의 id',
    type                       VARCHAR(20)                              NOT NULL COMMENT '스케줄 타입 (일정, 발주)',
    title                      VARCHAR(100)                             NOT NULL COMMENT '스케줄 타이틀',
    ordering_place             VARCHAR(100)                                 NULL COMMENT '발주처 정보',
    start_date                 DATETIME(6)                              NOT NULL COMMENT '스케줄 시작 날짜',
    end_date                   DATETIME(6)                                  NULL COMMENT '스케줄 끝 날짜',
    is_alarm_on                CHAR(1)                                  NOT NULL COMMENT '알람이 켜져 있는지 여부 (T/F)',
    is_deleted                 CHAR(1)                                  NOT NULL COMMENT '삭제 된 스케줄 여부 (T/F)',
    created_at                 DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    last_modified              DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '스케줄 정보 테이블';

CREATE INDEX `idx-business_schedule-business_id`
    ON business_schedule (business_id);