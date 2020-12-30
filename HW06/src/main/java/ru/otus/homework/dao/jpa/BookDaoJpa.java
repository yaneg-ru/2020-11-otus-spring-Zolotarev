package ru.otus.homework.dao.jpa;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.otus.homework.dao.EntityDao;
import ru.otus.homework.domain.Book;

@Repository
@RequiredArgsConstructor
public class BookDaoJpa implements EntityDao<Book> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        Query query = em.createQuery("select count(b) from Book b");
        return (long) query.getSingleResult();
    }

    @Override
    public void insert(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
        } else {
            em.merge(book);
        }
    }

    @Override
    public Optional<Book> getById(Long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> getAll() {
        var query = em.createQuery("select b from Book b ", Book.class);
        EntityGraph<?> entityGraph = em.getEntityGraph("book-entity-graph");
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteById(Long id) {
        em.remove(getById(id).orElseThrow(EntityExistsException::new));
    }

}
