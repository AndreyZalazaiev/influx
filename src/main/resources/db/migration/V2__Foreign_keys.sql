alter table company
    add constraint foreign key (id_user) references user (id);
alter table recommendation
    add constraint foreign key (id_company) references company (id);
alter table resource
    add constraint foreign key (id_company) references company (id);
alter table sales
    add constraint foreign key (id_resource) references resource (id);
alter table sales
    add constraint foreign key (id_company) references company (id);
alter table users_authorities
    add constraint foreign key (role_id) references roles (id);
alter table users_authorities
    add constraint foreign key (user_id) references user (id);