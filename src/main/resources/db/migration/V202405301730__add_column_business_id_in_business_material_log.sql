ALTER TABLE `business_material_log`
    ADD COLUMN `business_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '사업의 id' AFTER `id`;
