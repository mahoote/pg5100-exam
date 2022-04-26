insert into animal_types
values (nextval('animal_types_id_seq'), 'Mammal'),
       (nextval('animal_types_id_seq'), 'Bird'),
       (nextval('animal_types_id_seq'), 'Reptile');

insert into animal_breeds
values (nextval('animal_breeds_id_seq'), 'Golden Retriever', 1),
       (nextval('animal_breeds_id_seq'), 'Canary', 2),
       (nextval('animal_breeds_id_seq'), 'Bearded Dragon', 3);

insert into animals
values (nextval('animals_id_seq'), 'Fido', 7, 1, 'Sport and healthy.', now()),
       (nextval('animals_id_seq'), 'Happy', 2, 2, 'Flies like a champ.', now()),
       (nextval('animals_id_seq'), 'Smaug', 12, 3, 'Old and wise, but likes to stay active.', now());