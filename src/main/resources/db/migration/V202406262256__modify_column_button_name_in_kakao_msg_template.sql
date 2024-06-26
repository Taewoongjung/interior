ALTER TABLE kakao_msg_template
DROP COLUMN button_name,
ADD COLUMN button_info VARCHAR(1000) NOT NULL COMMENT '알림톡 버튼 정보';