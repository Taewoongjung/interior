ALTER TABLE `business` DROP COLUMN `host_id`;

ALTER TABLE `business`
    ADD COLUMN `company_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '회사 id' AFTER `name`;
