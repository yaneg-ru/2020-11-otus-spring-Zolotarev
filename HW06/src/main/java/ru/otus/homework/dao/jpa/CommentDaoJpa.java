package ru.otus.homework.dao.jpa;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ru.otus.homework.dao.EntityDao;
import ru.otus.homework.domain.Comment;

@Repository
public class CommentDaoJpa implements EntityDao<Comment> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        Query query = em.createQuery("select count(c) from Comment c");
        return (long) query.getSingleResult();
    }

    @Override
    public void insert(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
    }

    @Override
    public Optional<Comment> getById(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> getAll() {
        var query = em.createQuery("select c from Comment c", Comment.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(Long id) {
        em.remove(getById(id).orElseThrow(EntityExistsException::new));
    }
}
