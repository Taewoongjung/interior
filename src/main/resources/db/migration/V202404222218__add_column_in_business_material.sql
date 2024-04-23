ALTER TABLE `business_material`
    ADD COLUMN `usage_category` VARCHAR(50) NOT NULL default "" COMMENT '재료 사용 분류' AFTER `name`;

ALTER TABLE `business_material`
    ADD COLUMN `unit` VARCHAR(10) NOT NULL default "" COMMENT '재료 수량에 대한 단위' AFTER `amount`;
