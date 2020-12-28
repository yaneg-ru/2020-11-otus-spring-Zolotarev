package ru.otus.homework.dao.jpa;

import java.util.List;
import java.util.Optional;

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

    public List<Book> getAllByGenre(Long genreId) {
        var query = em.createQuery("select b from Book b where b.genre.id=:genreId", Book.class);
        EntityGraph<?> entityGraph = em.getEntityGraph("book-entity-graph");
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        query.setParameter("genreId", genreId);
        return query.getResultList();
    }

    public List<Book> getAllByAuthor(Long authorId) {
        var query = em.createQuery("select b from Book b where b.author.id=:authorId", Book.class);
        EntityGraph<?> entityGraph = em.getEntityGraph("book-entity-graph");
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        query.setParameter("authorId", authorId);
        return query.getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Query query = em.createQuery("delete from Book b where b.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
