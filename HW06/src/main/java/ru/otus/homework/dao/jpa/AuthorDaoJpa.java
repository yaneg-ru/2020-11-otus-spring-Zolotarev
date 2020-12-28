package ru.otus.homework.dao.jpa;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ru.otus.homework.dao.EntityDao;
import ru.otus.homework.domain.Author;

@Repository
public class AuthorDaoJpa implements EntityDao<Author> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        Query query = em.createQuery("select count(a) from Author a");
        return (long) query.getSingleResult();
    }

    @Override
    public void insert(Author author) {
        if (author.getId() == 0) {
            em.persist(author);
        } else {
            em.merge(author);
        }
    }

    @Override
    public Optional<Author> getById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> getAll() {
        var query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Query query = em.createQuery("delete from Author a where a.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
