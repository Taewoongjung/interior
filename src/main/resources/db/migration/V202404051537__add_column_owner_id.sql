ALTER TABLE company ADD COLUMN owner_id BIGINT(20) UNSIGNED NOT NULL COMMENT '회수 소유자의 id (userId)' AFTER name;