insert into author(id, name)
values ('1', 'Пушкин А.С.'),
       ('2', 'Толстой Л.Н.');

insert into genre(id, name)
values ('1', 'Поэзия'),
       ('2', 'Роман');

insert into book(id, name, author_id, genre_id)
values ('1', 'Евгений Онегин', '1', '1'),
       ('2', 'Война и мир', '2', '2');