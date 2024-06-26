ALTER TABLE kakao_msg_template
    ADD COLUMN third_party_type VARCHAR(20) null COMMENT '해당 템플릿 등록 사이트(Aligo, bizM 등등..)' AFTER template_code;