package ru.otus.homework.model;

import java.util.Objects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Answer {
    private boolean isRightAnswer;
    private boolean isInputAnswer;
    private String textOfAnswer;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Answer)) {
            return false;
        }
        Answer answer = (Answer) o;
        return isRightAnswer == answer.isRightAnswer &&
                isInputAnswer == answer.isInputAnswer &&
                Objects.equals(textOfAnswer, answer.textOfAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isRightAnswer, isInputAnswer, textOfAnswer);
    }
}
