package ru.otus.homework.dao;

import java.util.List;
import java.util.Optional;

import ru.otus.homework.domain.BaseNamedEntity;

public interface EntityDao<E extends BaseNamedEntity> {

    long count();

    void insert(E entity);

    Optional<E> getById(Long id);

    List<E> getAll();

    void deleteById(Long id);
}
