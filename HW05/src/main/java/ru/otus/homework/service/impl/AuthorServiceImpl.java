package ru.otus.homework.service.impl;

import org.springframework.stereotype.Service;

import ru.otus.homework.dao.jdbc.AuthorDaoJdbc;
import ru.otus.homework.domain.Author;
import ru.otus.homework.service.CrudServiceImpl;

@Service
public class AuthorServiceImpl extends CrudServiceImpl<Author, AuthorDaoJdbc> {

}
