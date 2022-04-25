insert into users
values (nextval('users_id_seq'), 'admin', now(), true, '$2a$12$IRjwrAviyElbBxZ5tfCz4OtP1QCPfXV7r.4r0ONr3cQrMSUXYoR2S');

insert into authorities
values (nextval('authorities_id_seq'), 'ADMIN'),
       (nextval('authorities_id_seq'), 'USER');

insert into users_authorities
values (1, 1);

insert into users_authorities
values (1, 2);