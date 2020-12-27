package ru.otus.homework.dao;

import java.util.List;

import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.BaseNamedEntity;

public interface EntityDao<E extends BaseNamedEntity> {

    int count();

    void insert(E entity);

    E getById(Long id);

    List<E> getAll();

    void deleteById(Long id);
}
