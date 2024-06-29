CREATE TABLE business_third_party_message
(
    id                         BIGINT(20) UNSIGNED                      NOT NULL AUTO_INCREMENT,
    business_id                BIGINT(20) UNSIGNED                      NOT NULL COMMENT '메시지를 보낸 사업 id',
    sender_id                  BIGINT(20) UNSIGNED                      NOT NULL COMMENT '보낸 사람의 id',
    kakao_msg_result_id        BIGINT(20) UNSIGNED                      NOT NULL COMMENT '알림톡 발송 결과 id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '사업쪽에서 보내는 써드파티 메시지';

CREATE INDEX `idx-business_third_party_message-business_id`
    ON business_third_party_message (business_id);