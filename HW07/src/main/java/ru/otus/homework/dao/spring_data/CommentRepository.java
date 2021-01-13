package ru.otus.homework.dao.spring_data;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.homework.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
