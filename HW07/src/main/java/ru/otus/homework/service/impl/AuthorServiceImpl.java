package ru.otus.homework.service.impl;

import org.springframework.stereotype.Service;

import ru.otus.homework.dao.spring_data.AuthorRepository;
import ru.otus.homework.domain.Author;
import ru.otus.homework.service.CrudServiceImpl;

@Service
public class AuthorServiceImpl extends CrudServiceImpl<Author, AuthorRepository> {

}
