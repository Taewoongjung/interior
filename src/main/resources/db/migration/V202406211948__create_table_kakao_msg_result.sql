CREATE TABLE kakao_msg_result
(
    id                         BIGINT(20) UNSIGNED                      NOT NULL AUTO_INCREMENT,
    template_name              VARCHAR(50)                              NOT NULL COMMENT '알림톡 템플릿 명',
    template_code              VARCHAR(20)                              NOT NULL COMMENT '알림톡 템플릿 코드',
    message_subject            VARCHAR(30)                              NOT NULL COMMENT '알림톡 제목',
    message                    VARCHAR(3000)                            NOT NULL COMMENT '알림톡 내용',
    message_type               CHAR(3)                                  NOT NULL COMMENT '전송 한 메시지 타입 (KKO: 카카오, SMS: 대체 단문)',
    receiver_phone             VARCHAR(30)                              NOT NULL COMMENT '수신자 전화번호',
    msg_id                     VARCHAR(20)                              NOT NULL COMMENT '전송 메시지 id',
    is_success                 CHAR(1)                                  NOT NULL COMMENT '알림톡 전송 성공 여부 (T/F)',
    created_at                 DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '카카오 알림톡 발송 결과 정보';

CREATE INDEX `idx-kakao_msg_result-created_at`
    ON kakao_msg_result (created_at);