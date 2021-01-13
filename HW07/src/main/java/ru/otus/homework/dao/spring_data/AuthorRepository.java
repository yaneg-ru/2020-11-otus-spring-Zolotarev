package ru.otus.homework.dao.spring_data;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.homework.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @SuppressWarnings("NullableProblems")
    @Override
    @EntityGraph(value = "author-entity-graph")
    Optional<Author> findById(Long id);
}
