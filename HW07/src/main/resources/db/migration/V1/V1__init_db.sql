create TABLE IF NOT EXISTS author
(
    id   bigint       NOT NULL,
    name varchar(128) NOT NULL,
    PRIMARY KEY (id)
);
create TABLE IF NOT EXISTS genre
(
    id   bigint       NOT NULL,
    name varchar(128) NOT NULL,
    PRIMARY KEY (id)
);
create TABLE IF NOT EXISTS comment
(
    id      bigint NOT NULL,
    name    text   NOT NULL,
    book_id bigint NOT NULL,
    PRIMARY KEY (id)
);
create TABLE IF NOT EXISTS book
(
    id        bigint       NOT NULL,
    author_id bigint       NOT NULL,
    genre_id  bigint       NOT NULL,
    name      varchar(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (author_id) REFERENCES author (id),
    FOREIGN KEY (genre_id) REFERENCES genre (id)
);