create table company
(
    id      integer      not null,
    id_user integer      not null,
    name    varchar(255) not null,
    primary key (id)
);
create table device
(
    device_code varchar(255) not null,
    id_company  integer      not null,
    primary key (device_code)
);
create table hibernate_sequence
(
    next_val bigint
);
insert into hibernate_sequence
values (1);
create table recommendation
(
    id         integer not null,
    date       datetime(6),
    id_company integer not null,
    period     integer  default 7,
    text       varchar(255),
    primary key (id)
);
create table resource
(
    id         integer not null,
    id_company integer not null,
    name       varchar(255),
    price      varchar(255),
    primary key (id)
);
create table roles
(
    id   integer not null,
    role varchar(255),
    primary key (id)
);
create table sales
(
    id          integer     not null,
    date        datetime(6) not null,
    id_company  integer     not null,
    id_resource integer     not null,
    primary key (id)
);
create table user
(
    id                 integer not null,
    email              varchar(255),
    email_confirmation varchar(255),
    password           varchar(255),
    username           varchar(255),
    primary key (id)
);
create table users_authorities
(
    user_id integer not null,
    role_id integer not null
);
create table visit
(
    id         integer not null,
    count      int default 0,
    date       date    not null,
    id_company integer not null,
    primary key (id)
);