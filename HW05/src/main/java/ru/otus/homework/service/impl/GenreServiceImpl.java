package ru.otus.homework.service.impl;

import org.springframework.stereotype.Service;

import ru.otus.homework.dao.jdbc.GenreDaoJdbc;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.service.CrudServiceImpl;

@Service
public class GenreServiceImpl extends CrudServiceImpl<Genre, GenreDaoJdbc> {

}
