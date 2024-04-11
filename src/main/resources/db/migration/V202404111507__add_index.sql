ALTER TABLE `business` ADD INDEX `idx-business-host_id` (host_id);
ALTER TABLE `business` ADD INDEX `idx-business-customer_id` (customer_id);

ALTER TABLE `business_material` ADD INDEX `idx-business_material-business_id` (business_id);