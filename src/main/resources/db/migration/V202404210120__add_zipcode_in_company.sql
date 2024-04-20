ALTER TABLE `company`
    ADD COLUMN `zipcode` VARCHAR(5) NOT NULL default 0 COMMENT '회사의 우편번호' AFTER `name`;
