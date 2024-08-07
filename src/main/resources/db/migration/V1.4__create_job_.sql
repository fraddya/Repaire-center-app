create table job
(id bigint not null auto_increment,
 status varchar(10),
created_by bigint,
last_modified_by bigint,
created_date date,
created_at datetime(6),
last_modified_at datetime(6),
job_date_and_time datetime(6),
job_status varchar(255),
job_description varchar(1000),
estimate_time decimal(19,2),
estimate_price decimal(19,2),
actual_price decimal(19,2),
vehicle_id bigint,
customer_id bigint,
assign_employee_id bigint,
CONSTRAINT fk_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle(id),
 CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES user(id),
 CONSTRAINT fk_assign_employee FOREIGN KEY (assign_employee_id) REFERENCES user(id),
 primary key (id)) engine=InnoDB AUTO_INCREMENT=1;


create table repairer_items
(id bigint not null auto_increment,
 status varchar(10),
 created_by bigint,
 last_modified_by bigint,
 created_date date,
 created_at datetime(6),
 last_modified_at datetime(6),
 description varchar(1000),
 quantity integer,
 estimate_price decimal(19,2),
 part_id bigint,
 job_id bigint,
 CONSTRAINT fk_part FOREIGN KEY (part_id) REFERENCES vehicle_part(id),
 CONSTRAINT fk_job FOREIGN KEY (job_id) REFERENCES job(id),
 primary key (id)) engine=InnoDB AUTO_INCREMENT=1;
