package ru.otus.homework.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import ru.otus.homework.dao.EntityDao;
import ru.otus.homework.domain.Genre;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class GenreDaoJdbc implements EntityDao<Genre> {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations)
    {
        this.jdbc = namedParameterJdbcOperations.getJdbcOperations();
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from genre", Integer.class);
    }

    @Override
    public void insert(Genre genre) {
        jdbc.update("insert into genre (id, name) values (?, ?)", genre.getId(), genre.getName());
    }

    @Override
    public Genre getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select * from genre where id = :id", params, new GenreMapper()
        );
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select * from genre", new GenreMapper());
    }

    @Override
    public void deleteById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from genre where id = :id", params);
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            return Genre.builder()
                    .id((Long) resultSet.getObject("id"))
                    .name(resultSet.getString("name"))
                    .build();
        }
    }
}
