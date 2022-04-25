create table animal_types
(
    id   bigserial primary key,
    animal_type varchar(255)
);

create table animal_breeds
(
    id    bigserial primary key,
    breed varchar(255),
    type_id bigint,
    constraint fk_type
        foreign key (type_id)
            references animal_types (id)
);

create table animals
(
    id       bigserial primary key,
    animal_name     varchar(255),
    age      integer,
    breed_id bigint,
    health   varchar(500),
    created  timestamp,
    constraint fk_breed
        foreign key (breed_id)
            references animal_breeds (id)
);
