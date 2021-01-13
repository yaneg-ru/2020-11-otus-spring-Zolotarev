package ru.otus.homework.service;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.homework.domain.BaseNamedEntity;

@RequiredArgsConstructor
@Slf4j
public abstract class CrudServiceImpl<E extends BaseNamedEntity, R extends JpaRepository<E, Long>> implements CrudService<E> {

    public static final String START_GETTING_COUNT_OF_ENTITIES = "Start getting count of entities";
    public static final String START_CREATING = "Start creating {}: {}";
    public static final String START_UPDATING = "Start updating {}: {}";
    public static final String START_GETTING_BY_ID = "Start getting by id {}";
    public static final String START_GETTING_ALL = "Start getting all";
    public static final String START_DELETING_BY_ID = "Start deleting by id {}";
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected R repository;

    @Override
    public long count() {
        log.trace(START_GETTING_COUNT_OF_ENTITIES);
        return repository.count();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public E create(E entity) {
        log.trace(START_CREATING, entity.getClass().getSimpleName(), entity);
        Long id = repository.count() + 1L;
        entity.setId(id);
        repository.save(entity);
        return repository.findById(id).get();
    }

    @Override
    public E update(E entity) {
        log.trace(START_UPDATING, entity.getClass().getSimpleName(), entity);
        repository.findById(entity.getId()).orElseThrow(EntityExistsException::new);
        repository.save(entity);
        return entity;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public E getById(Long id) {
        log.trace(START_GETTING_BY_ID, id);
        return repository.findById(id).orElseGet(null);
    }

    @Override
    public List<E> getAll() {
        log.trace(START_GETTING_ALL);
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        log.trace(START_DELETING_BY_ID, id);
        repository.deleteById(id);
    }
}
