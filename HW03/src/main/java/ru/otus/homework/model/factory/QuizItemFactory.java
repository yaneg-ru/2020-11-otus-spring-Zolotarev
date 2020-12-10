package ru.otus.homework.model.factory;

import ru.otus.homework.model.QuizItem;

public interface QuizItemFactory {
    QuizItem createQuizItem(String keyOfQuizItem, String lineFromSource);
}
