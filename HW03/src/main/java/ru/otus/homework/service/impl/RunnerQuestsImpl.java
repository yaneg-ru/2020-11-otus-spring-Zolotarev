package ru.otus.homework.service.impl;

import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ru.otus.homework.config.AppProps;
import ru.otus.homework.model.Answer;
import ru.otus.homework.model.Question;
import ru.otus.homework.service.RunnerQuests;

@Service
public class RunnerQuestsImpl implements RunnerQuests {

    private static final String HELLO = "Hello! Let's start our Quest!!!";
    private static final String WHATS_YOUR_FIRST_NAME = "What's your first name?";
    private static final String WHATS_YOUR_SECOND_NAME = "What's your second name?";
    private static final String LET_S_GO = "Let's GO %s %s!!! ";
    private static final String SUPER = "Super! You are done it!";
    private static final String SORRY = "Sorry! But you are stupid!";

    private static final String QUESTION = "Question:";
    private static final String ANSWER_TRUE = "AnswerTrue:";
    private static final String ANSWER_FALSE = "AnswerFalse:";
    private static final String ANSWER_INPUT = "AnswerInput:";
    private static final String ENTER_ANSWER = "Enter your answer:" + System.lineSeparator();

    private final BufferedReaderHelperImpl bufferedReaderHelper;
    private final AppProps appProps;
    private final MessageSource messageSource;

    public RunnerQuestsImpl(BufferedReaderHelperImpl bufferedReaderHelper, AppProps appProps,
                            MessageSource messageSource) {
        this.bufferedReaderHelper = bufferedReaderHelper;
        this.appProps = appProps;
        this.messageSource = messageSource;
    }

    @Override
    public List<Question> readQuestions() {
        List<Question> questionList = new ArrayList<>();
        try {
            BufferedReader reader = bufferedReaderHelper.getBufferedReaderFromFile();
            Question question = null;
            List<Answer> answerList = new ArrayList<>();
            for (String line; (line = reader.readLine()) != null; ) {
                // found first question in file
                if (line.startsWith(QUESTION) && question == null) {
                    question = Question.builder()
                            .textOfQuestion(
                                    messageSource.getMessage(
                                            line.substring(QUESTION.length()),
                                            null,
                                            appProps.getLocale()
                                    )
                            )
                            .build();
                    continue;
                }
                // found second question in file
                if (line.startsWith(QUESTION) && question != null) {
                    question.setAnswers(answerList);
                    questionList.add(question);
                    question = Question.builder()
                            .textOfQuestion(
                                    messageSource.getMessage(
                                            line.substring(QUESTION.length()),
                                            null,
                                            appProps.getLocale()
                                    )
                            )
                            .build();
                    answerList = new ArrayList<>();
                }
                // found true answer in file
                if (line.startsWith(ANSWER_TRUE)) {
                    Answer answer = Answer.builder()
                            .textOfAnswer(
                                    messageSource.getMessage(
                                            line.substring(ANSWER_TRUE.length()),
                                            null,
                                            appProps.getLocale()
                                    )
                            )
                            .isRightAnswer(true)
                            .build();
                    answerList.add(answer);
                }
                // found input answer in file
                if (line.startsWith(ANSWER_INPUT)) {
                    Answer answer = Answer.builder()
                            .textOfAnswer(
                                    messageSource.getMessage(
                                            line.substring(ANSWER_INPUT.length()),
                                            null,
                                            appProps.getLocale()
                                    )
                            )
                            .isInputAnswer(true)
                            .build();
                    answerList.add(answer);
                }
                // found false answer in file
                if (line.startsWith(ANSWER_FALSE)) {
                    Answer answer = Answer.builder()
                            .textOfAnswer(
                                    messageSource.getMessage(
                                            line.substring(ANSWER_FALSE.length()),
                                            null,
                                            appProps.getLocale()
                                    )
                            )
                            .isRightAnswer(false)
                            .build();
                    answerList.add(answer);
                }
            }
            Objects.requireNonNull(question).setAnswers(answerList);
            questionList.add(question);
            bufferedReaderHelper.closeBufferedReader(reader);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        return questionList;
    }

    @Override
    public void runQuest() {
        var reader = bufferedReaderHelper.getBufferedReaderFromSystemIn();
        System.out.println(HELLO);
        System.out.println(WHATS_YOUR_FIRST_NAME);
        String firstName = null;
        try {
            firstName = reader.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(WHATS_YOUR_SECOND_NAME);
        String secondName = null;
        try {
            secondName = reader.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(format(LET_S_GO, firstName, secondName) + System.lineSeparator());
        List<Question> questionList = readQuestions();
        int rightAnswers = 0;
        for (Question question : questionList) {
            rightAnswers += askQuestion(question, reader);
        }
        if (rightAnswers >= appProps.getMinimumNumberOfCorrectAnswers()) {
            System.out.println(SUPER);
        } else {
            System.out.println(SORRY);
        }
        try {
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int askQuestion(Question question, BufferedReader reader) {
        System.out.println(question);
        System.out.println(ENTER_ANSWER);
        String answer;
        try {
            answer = reader.readLine();
        }
        catch (IOException e) {
            answer = "";
        }
        int rightAnswers = 0;
        int allRightAnswers = 0;
        if (question.getAnswers().size() == 1) {
            allRightAnswers++;
            if (answer.equals(question.getAnswers().get(0).getTextOfAnswer())) {
                rightAnswers = 1;
            }
        } else {
            AtomicInteger counter = new AtomicInteger(0);
            for (Answer element : question.getAnswers()) {
                int currentNumber = counter.incrementAndGet();
                if (element.isRightAnswer()) {
                    allRightAnswers++;
                    if (answer.contains(String.valueOf(currentNumber))) {
                        rightAnswers++;
                    }
                }
            }
        }
        System.out.println(rightAnswers + " / " + allRightAnswers + System.lineSeparator());
        return rightAnswers;
    }
}