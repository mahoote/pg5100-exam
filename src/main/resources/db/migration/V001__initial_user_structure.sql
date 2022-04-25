create table users
(
    id       bigserial primary key,
    username varchar(50),
    created  timestamp,
    enabled  bool,
    password varchar(100)
);
create table authorities
(
    id    bigserial primary key,
    title varchar(50)
);
create table users_authorities
(
    user_id      bigint,
    authority_id bigint,
    constraint fk_user
        foreign key (user_id)
            references users (id),
    constraint fk_authorities
        foreign key (authority_id)
            references authorities (id)
);