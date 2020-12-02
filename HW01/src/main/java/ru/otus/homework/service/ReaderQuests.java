package ru.otus.homework.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.otus.homework.model.Answer;
import ru.otus.homework.model.Question;

public class ReaderQuests {

    private final String path;
    private static final String QUESTION = "Question:";
    private static final String ANSWER_TRUE = "AnswerTrue:";
    private static final String ANSWER_FALSE = "AnswerFalse:";

    public ReaderQuests(String path) {
        this.path = path;
    }

    public List<Question> readQuestions() {
        List<Question> questionList = new ArrayList<>();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(path);
        try {
            InputStreamReader streamReader =
                    new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            Question question = null;
            List<Answer> answerList = new ArrayList<>();
            for (String line; (line = reader.readLine()) != null; ) {
                // found first question in file
                if (line.startsWith(QUESTION) && question == null) {
                    question = Question.builder()
                            .textOfQuestion(line.substring(QUESTION.length()))
                            .build();
                    continue;
                }
                // found second question in file
                if (line.startsWith(QUESTION) && question != null) {
                    question.setAnswers(answerList);
                    questionList.add(question);
                    question = Question.builder()
                            .textOfQuestion(line.substring(QUESTION.length()))
                            .build();
                    answerList = new ArrayList<>();
                }
                // found true answer in file
                if (line.startsWith(ANSWER_TRUE) && question != null) {
                    Answer answer = Answer.builder()
                            .textOfAnswer(line.substring(ANSWER_TRUE.length()))
                            .isRightAnswer(true)
                            .build();
                    answerList.add(answer);
                }
                // found false answer in file
                if (line.startsWith(ANSWER_FALSE) && question != null) {
                    Answer answer = Answer.builder()
                            .textOfAnswer(line.substring(ANSWER_FALSE.length()))
                            .isRightAnswer(false)
                            .build();
                    answerList.add(answer);
                }
            }
            Objects.requireNonNull(question).setAnswers(answerList);
            questionList.add(question);
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return questionList;
    }
}
