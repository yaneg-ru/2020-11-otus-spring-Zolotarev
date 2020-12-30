package ru.otus.homework.dao.jpa;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ru.otus.homework.dao.EntityDao;
import ru.otus.homework.domain.Genre;

@Repository
public class GenreDaoJpa implements EntityDao<Genre> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        Query query = em.createQuery("select count(g) from Genre g");
        return (long) query.getSingleResult();
    }

    @Override
    public void insert(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
        } else {
            em.merge(genre);
        }
    }

    @Override
    public Optional<Genre> getById(Long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> getAll() {
        var query = em.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(Long id) {
        em.remove(getById(id).orElseThrow(EntityExistsException::new));
    }
}
