create table vehicle_part
(id bigint not null auto_increment,
 status varchar(10),
created_by bigint,
last_modified_by bigint,
created_date date,
created_at datetime(6),
last_modified_at datetime(6),
vehicle_no varchar(255),
model varchar(255),
year varchar(255),
color varchar(255),
type varchar(255),
description varchar(255),
 brand_id bigint,
 user_id bigint,
 CONSTRAINT fk_brand FOREIGN KEY (brand_id) REFERENCES brand(id),
 CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user(id),
 primary key (id)) engine=InnoDB AUTO_INCREMENT=1;
