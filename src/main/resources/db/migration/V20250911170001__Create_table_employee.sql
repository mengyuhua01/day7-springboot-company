create table IF NOT EXISTS db_springboot.t_employee
(
    id            bigint auto_increment
        primary key,
    active_status bit          not null,
    age           int          not null,
    gender        varchar(255) null,
    name          varchar(255) null,
    salary        double       not null,
    company_id    bigint       null,
    constraint FKt6559x54b671oj48h6cy21j85
        foreign key (company_id) references db_springboot.t_company (id)
);

