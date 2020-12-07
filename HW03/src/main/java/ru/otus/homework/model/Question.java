package ru.otus.homework.model;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Question {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        Question question = (Question) o;
        return Objects.equals(textOfQuestion, question.textOfQuestion) &&
                Objects.equals(answers, question.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textOfQuestion, answers);
    }
}
