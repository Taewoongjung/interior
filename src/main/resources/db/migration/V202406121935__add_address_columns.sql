ALTER TABLE business
    ADD COLUMN zipcode         varchar(5) default '0' not null comment '사업지 우편번호' AFTER is_deleted,
    ADD COLUMN address         varchar(50) not null comment '사업지 메인 주소' AFTER zipcode,
    ADD COLUMN sub_address     varchar(50) not null comment '사업지 부주소' AFTER address,
    ADD COLUMN building_number varchar(50) not null comment '사업지 주소의 빌딩번호' AFTER sub_address;