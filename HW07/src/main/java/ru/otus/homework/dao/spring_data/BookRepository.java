package ru.otus.homework.dao.spring_data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.homework.domain.Book;

@SuppressWarnings("NullableProblems")
public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    @EntityGraph(value = "book-entity-graph")
    Optional<Book> findById(Long id);

    @EntityGraph(value = "book-entity-graph")
    @Override
    List<Book> findAll();

}
