package ru.otus.homework.service;

import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ru.otus.homework.model.Answer;
import ru.otus.homework.model.Question;

@Component
public class RunnerQuestsImpl implements RunnerQuests {

    private static final String HELLO = "Hello! Let's start our Quest!!!";
    private static final String WHATS_YOUR_FIRST_NAME = "What's your first name?";
    private static final String WHATS_YOUR_SECOND_NAME = "What's your second name?";
    private static final String LET_S_GO = "Let's GO %s %s!!! ";
    private static final String  SUPER = "Super! You are done it!";
    private static final String SORRY = "Sorry! But you are stupid!";

    private static final String QUESTION = "Question:";
    private static final String ANSWER_TRUE = "AnswerTrue:";
    private static final String ANSWER_FALSE = "AnswerFalse:";
    private static final String ANSWER_INPUT = "AnswerInput:";

    @Value("${pathFileQuestions}")
    private String pathFileQuestions;

    @Value("${minimumNumberOfCorrectAnswers}")
    private int minimumNumberOfCorrectAnswers;

    public List<Question> readQuestions() {
        List<Question> questionList = new ArrayList<>();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(pathFileQuestions);
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
                if (line.startsWith(ANSWER_TRUE)) {
                    Answer answer = Answer.builder()
                            .textOfAnswer(line.substring(ANSWER_TRUE.length()))
                            .isRightAnswer(true)
                            .build();
                    answerList.add(answer);
                }
                // found input answer in file
                if (line.startsWith(ANSWER_INPUT)) {
                    Answer answer = Answer.builder()
                            .textOfAnswer(line.substring(ANSWER_INPUT.length()))
                            .isInputAnswer(true)
                            .build();
                    answerList.add(answer);
                }
                // found false answer in file
                if (line.startsWith(ANSWER_FALSE)) {
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

    @Override
    public void runQuest() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(HELLO);
        System.out.println(WHATS_YOUR_FIRST_NAME);
        String firstName = reader.readLine();
        System.out.println(WHATS_YOUR_SECOND_NAME);
        String secondName = reader.readLine();
        System.out.println(format(LET_S_GO, firstName, secondName) + System.lineSeparator());
        List<Question> questionList = readQuestions();
        int rightAnswers = 0;
        for (Question question : questionList) {
            rightAnswers += question.askQuestion(reader);
        }
        if (rightAnswers >= minimumNumberOfCorrectAnswers) {
            System.out.println(SUPER);
        } else {
            System.out.println(SORRY);
        }
        reader.close();
    }
}
