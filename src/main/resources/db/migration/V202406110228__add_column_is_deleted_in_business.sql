ALTER TABLE business
    ADD COLUMN is_deleted VARCHAR(1) DEFAULT 'F' NOT NULL COMMENT '사업 삭제 여부 (F: 삭제 안됨, T: 삭제)' AFTER status_detail;
