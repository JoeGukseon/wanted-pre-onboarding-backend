create table member
(
    member_id BIGINT(19) auto_increment
        primary key,
    email     VARCHAR(255) not null,
    password  VARCHAR(255) not null,
    constraint UK_mbmcqelty0fbrvxp1q58dn57t
        unique (email)
);

create table member_roles
(
    member_id BIGINT(19)   not null,
    roles     VARCHAR(255) null,
    constraint FKet63dfllh4o5qa9qwm7f5kx9x
        foreign key (member_id) references member (member_id)
);

create table post
(
    post_id   BIGINT(19) auto_increment
        primary key,
    content   VARCHAR(255) null,
    title     VARCHAR(255) null,
    member_id BIGINT(19)   null,
    constraint FK83s99f4kx8oiqm3ro0sasmpww
        foreign key (member_id) references member (member_id)
);
