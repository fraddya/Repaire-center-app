create table vehicle_part
(id bigint not null auto_increment,
 status varchar(10),
created_by bigint,
last_modified_by bigint,
created_date date,
created_at datetime(6),
last_modified_at datetime(6),
name varchar(100),
code varchar(100),
description varchar(255),
 primary key (id)) engine=InnoDB AUTO_INCREMENT=1;
