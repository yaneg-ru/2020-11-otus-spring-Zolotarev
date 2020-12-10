package ru.otus.homework.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import ru.otus.homework.model.Question;

public interface RunnerQuests {
    List<Question> readQuestions() throws IOException;
    void  runQuest() throws IOException;
    int askQuestion(Question question, BufferedReader reader);
}
