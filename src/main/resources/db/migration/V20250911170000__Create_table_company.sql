create table IF NOT EXISTS db_springboot.t_company
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null comment '公司名字'
);

