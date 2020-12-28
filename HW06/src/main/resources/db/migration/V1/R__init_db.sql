insert into author(id, name)
values ('1', 'Пушкин А.С.'),
       ('2', 'Толстой Л.Н.');

insert into genre(id, name)
values ('1', 'Поэзия'),
       ('2', 'Роман');

insert into book(id, name, author_id, genre_id)
values ('1', 'Евгений Онегин', '1', '1'),
       ('2', 'Война и мир', '2', '2');

insert into comment(id, name, book_id)
values ('1', 'Comment 1', 1),
       ('2', 'Comment 2', 1),
       ('3', 'Comment 3', 1),
       ('4', 'Comment 4', 1),
       ('5', 'Comment 5', 1),
       ('6', 'Comment 6', 1),
       ('7', 'Comment 7', 1),
       ('8', 'Comment 8', 1),
       ('9', 'Comment 9', 1),
       ('10', 'Comment 10', 1),
       ('11', 'Comment 11', 1),
       ('12', 'Comment 12', 1),
       ('13', 'Comment 13', 1),
       ('14', 'Comment 14', 1),
       ('15', 'Comment 15', 1);