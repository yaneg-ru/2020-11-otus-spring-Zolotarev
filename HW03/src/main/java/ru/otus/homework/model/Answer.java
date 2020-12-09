package ru.otus.homework.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Answer implements QuizItem{
    private final boolean isRightAnswer;
    private final boolean isInputAnswer;
    private final String textOfAnswer;
}
