package ru.otus.homework.service;

import java.util.List;

import ru.otus.homework.domain.BaseNamedEntity;

public interface CrudService<E extends BaseNamedEntity> {

    long count();

    E create(E entity);

    E update(E entity);

    E getById(Long id);

    List<E> getAll();

    void deleteById(Long id);
}
