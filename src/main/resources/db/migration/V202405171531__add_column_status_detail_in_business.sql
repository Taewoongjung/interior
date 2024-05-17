ALTER TABLE `business`
    ADD COLUMN `status_detail` VARCHAR(50) NULL COMMENT '사업 상태 상세' AFTER `status`;