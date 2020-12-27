package ru.otus.homework.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.otus.homework.dao.EntityDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements EntityDao<Book> {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from book", Integer.class);
    }

    @Override
    public void insert(Book book) {
        jdbc.update("insert into book (id, name, author_id, genre_id) values (?, ?, ?, ?)",
                    book.getId(), book.getName(), book.getAuthor().getId(), book.getGenre().getId()
        );
    }

    @Override
    public Book getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return namedParameterJdbcOperations.queryForObject(
                    "select book.id as book_id, book.name as book_name, "
                            + "author.id as author_id, author.name as author_name, "
                            + "genre.id as genre_id, genre.name as genre_name from book "
                            + "join author on author.id = book.author_id "
                            + "join genre on genre.id = book.genre_id "
                            + "where book.id = :id", params,
                    new BookMapper()
            );
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query(
                "select book.id as book_id, book.name as book_name, "
                        + "author.id as author_id, author.name as author_name, "
                        + "genre.id as genre_id, genre.name as genre_name from book "
                        + "join author on author.id = book.author_id "
                        + "join genre on genre.id = book.genre_id ",
                new BookMapper()
        );
    }

    public List<Book> getAllByGenre(Long genreId) {
        Map<String, Object> params = Collections.singletonMap("id", genreId);
        return namedParameterJdbcOperations.query(
                "select book.id as book_id, book.name as book_name, "
                        + "author.id as author_id, author.name as author_name, "
                        + "genre.id as genre_id, genre.name as genre_name from book "
                        + "join author on author.id = book.author_id "
                        + "join genre on genre.id = book.genre_id "
                        + "where genre.id = :id", params, new BookMapper()
        );

    }

    public List<Book> getAllByAuthor(Long authorId) {
        Map<String, Object> params = Collections.singletonMap("id", authorId);
        return namedParameterJdbcOperations.query(
                "select book.id as book_id, book.name as book_name, "
                        + "author.id as author_id, author.name as author_name, "
                        + "genre.id as genre_id, genre.name as genre_name from book "
                        + "join author on author.id = book.author_id "
                        + "join genre on genre.id = book.genre_id "
                        + "where author.id = :id", params, new BookMapper()
        );
    }

    @Override
    public void deleteById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from book where id = :id", params);
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Author author = Author.builder()
                    .id((Long) resultSet.getObject("author_id"))
                    .name(resultSet.getString("author_name"))
                    .build();
            Genre genre = Genre.builder()
                    .id((Long) resultSet.getObject("genre_id"))
                    .name(resultSet.getString("genre_name"))
                    .build();
            return Book.builder()
                    .id((Long) resultSet.getObject("book_id"))
                    .name(resultSet.getString("book_name"))
                    .author(author)
                    .genre(genre)
                    .build();
        }
    }
}
