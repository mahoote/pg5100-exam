insert into users
values (nextval('users_id_seq'), 'admin', now(), true, '$2a$12$6wZBAkNhsTI2efyAL0914eIIXINx.Y01Mx5lUEMfVotRkk1Cjyg3e');

insert into authorities
values (nextval('authorities_id_seq'), 'ADMIN'),
       (nextval('authorities_id_seq'), 'USER');

insert into users_authorities
values (1, 1);

insert into users_authorities
values (1, 2);