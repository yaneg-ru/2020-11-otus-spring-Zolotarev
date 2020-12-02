package ru.otus.homework.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Question {
    private static final String ENTER_ANSWER = "Enter your answer:" + System.lineSeparator();
    private String textOfQuestion;
    private List<Answer> answers;

    @Override
    public String toString() {
        AtomicInteger counter = new AtomicInteger(0);
        return textOfQuestion
                + System.lineSeparator()
                + answers.stream()
                .filter(answer -> !answer.isInputAnswer())
                .map(Answer::getTextOfAnswer)
                .map(s -> counter.incrementAndGet() + " - " + s)
                .collect(Collectors.joining(System.lineSeparator()))
                + System.lineSeparator();
    }

    public int askQuestion(BufferedReader reader) throws IOException {
        System.out.println(this.toString());
        System.out.println(ENTER_ANSWER);
        String answer = reader.readLine();
        int rightAnswers = 0;
        int allRightAnswers = 0;
        if (answers.size() == 1) {
            allRightAnswers++;
            if (answer.equals(answers.get(0).getTextOfAnswer())) {
                rightAnswers = 1;
            }
        } else {
            AtomicInteger counter = new AtomicInteger(0);
            for (Answer element : answers) {
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
