package ru.otus.homework.model;

import java.util.List;
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
        return textOfQuestion
                + System.lineSeparator()
                +  answers.stream()
                .map(Answer::getTextOfAnswer)
                .map(s -> " - " + s)
                .collect(Collectors.joining(System.lineSeparator()))
                + System.lineSeparator();
    }
}
