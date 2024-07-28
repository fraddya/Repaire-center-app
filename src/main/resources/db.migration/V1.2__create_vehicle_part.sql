create table vehicle_part
(id bigint not null auto_increment,
 status varchar(10),
created_by bigint,
last_modified_by bigint,
created_date date,
created_at datetime(6),
last_modified_at datetime(6),
part_code varchar(100),
name varchar(100),
price decimal(19,2),
description varchar(255),
brand_id bigint,
CONSTRAINT brand_id FOREIGN KEY (brand_id) REFERENCES brand (id),
 primary key (id)) engine=InnoDB AUTO_INCREMENT=1;
