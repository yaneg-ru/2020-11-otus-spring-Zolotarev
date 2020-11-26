package ru.otus.homework.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Answer {
    private boolean isRightAnswer;
    private String textOfAnswer;
}
