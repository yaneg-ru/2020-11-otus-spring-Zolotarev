package ru.otus.homework.service;

import java.io.BufferedReader;
import java.util.List;

import ru.otus.homework.model.Question;

public interface RunnerQuests {
    List<Question> readQuestions();
    void  runQuest();
    int askQuestion(Question question, BufferedReader reader);
}
