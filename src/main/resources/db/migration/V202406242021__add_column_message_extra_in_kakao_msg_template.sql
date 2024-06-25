ALTER TABLE kakao_msg_template
    ADD COLUMN message_extra VARCHAR(3000) null COMMENT '메시지 부가정보' AFTER template_code;