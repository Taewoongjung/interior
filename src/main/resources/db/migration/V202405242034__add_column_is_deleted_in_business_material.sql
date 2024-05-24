ALTER TABLE `business_material`
    ADD COLUMN `is_deleted` VARCHAR(1) NOT NULL DEFAULT 'F' COMMENT '재료 삭제 여부 (F: 삭제 안됨, T: 삭제)' AFTER `memo`;
