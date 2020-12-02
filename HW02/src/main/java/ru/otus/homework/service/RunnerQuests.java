package ru.otus.homework.service;

import java.io.IOException;
import java.util.List;

import ru.otus.homework.model.Question;

public interface RunnerQuests {
    List<Question> readQuestions();
    void  runQuest() throws IOException;
}
