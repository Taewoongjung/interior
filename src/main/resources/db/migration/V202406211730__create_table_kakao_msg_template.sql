CREATE TABLE kakao_msg_template
(
    id                         BIGINT(20) UNSIGNED                      NOT NULL AUTO_INCREMENT,
    template_name              VARCHAR(50)                              NOT NULL COMMENT '알림톡 템플릿 명',
    template_code              VARCHAR(20)                              NOT NULL COMMENT '알림톡 템플릿 코드',
    message_subject            VARCHAR(30)                              NOT NULL COMMENT '알림톡 제목',
    message                    VARCHAR(3000)                            NOT NULL COMMENT '알림톡 내용',
    replace_message_subject    VARCHAR(30)                              NOT NULL COMMENT '알림톡 대체 제목',
    replace_message            VARCHAR(3000)                            NOT NULL COMMENT '알림톡 대체 내용',
    button_name                VARCHAR(30)                              NOT NULL COMMENT '알림톡 버튼명',
    button_link_type           CHAR(2)                                  NOT NULL COMMENT '알림톡 버튼 링크 타입 (AL, WL, AC, DS, BK, MD 중에서 1개)',
    created_at                 DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    last_modified              DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '카카오 알림톡 발송 템플릿 정보';
