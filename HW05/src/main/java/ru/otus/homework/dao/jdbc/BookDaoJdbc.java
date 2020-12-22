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
import ru.otus.homework.domain.Book;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements EntityDao<Book> {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final AuthorDaoJdbc authorDaoJdbc;
    private final GenreDaoJdbc genreDaoJdbc;

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
                    "select * from book where id = :id", params, new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select * from book", new BookMapper());
    }

    public List<Book> getAllByGenre(Long genreId) {
        Map<String, Object> params = Collections.singletonMap("id", genreId);
        return namedParameterJdbcOperations.query(
                "select * from book where genre_id = :id", params, new BookMapper()
        );

    }

    public List<Book> getAllByAuthor(Long authorId) {
        Map<String, Object> params = Collections.singletonMap("id", authorId);
        return namedParameterJdbcOperations.query(
                "select * from book where author_id = :id", params, new BookMapper()
        );
    }

    @Override
    public void deleteById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from book where id = :id", params);
    }

    private class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            return Book.builder()
                    .id((Long) resultSet.getObject("id"))
                    .name(resultSet.getString("name"))
                    .author(authorDaoJdbc.getById((Long) resultSet.getObject("author_id")))
                    .genre(genreDaoJdbc.getById((Long) resultSet.getObject("genre_id")))
                    .build();
        }
    }
}
