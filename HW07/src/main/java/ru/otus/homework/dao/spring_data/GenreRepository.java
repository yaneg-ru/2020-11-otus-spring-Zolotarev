package ru.otus.homework.dao.spring_data;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.homework.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    @SuppressWarnings("NullableProblems")
    @EntityGraph(value = "genre-entity-graph")
    @Override
    Optional<Genre> findById(Long id);

}
