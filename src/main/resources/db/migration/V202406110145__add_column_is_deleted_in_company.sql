ALTER TABLE company
    ADD COLUMN is_deleted VARCHAR(1) DEFAULT 'F' NOT NULL COMMENT '사업체 삭제 여부 (F: 삭제 안됨, T: 삭제)' AFTER tel;
