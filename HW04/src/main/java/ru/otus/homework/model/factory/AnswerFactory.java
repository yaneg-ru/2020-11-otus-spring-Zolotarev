package ru.otus.homework.model.factory;

import static ru.otus.homework.service.impl.RunnerQuestsImpl.ANSWER_INPUT;
import static ru.otus.homework.service.impl.RunnerQuestsImpl.ANSWER_TRUE;

import org.springframework.context.MessageSource;

import ru.otus.homework.config.AppProps;
import ru.otus.homework.model.Answer;

public class AnswerFactory implements QuizItemFactory {

    private final AppProps appProps;
    private final MessageSource messageSource;

    public AnswerFactory(AppProps appProps, MessageSource messageSource) {
        this.appProps = appProps;
        this.messageSource = messageSource;
    }

    @Override
    public Answer createQuizItem(String keyOfQuizItem, String lineFromSource) {
        return Answer.builder()
                .textOfAnswer(
                        messageSource.getMessage(
                                lineFromSource.substring(keyOfQuizItem.length()),
                                null,
                                appProps.getLocale()
                        )
                )
                .isRightAnswer(keyOfQuizItem.equals(ANSWER_TRUE))
                .isInputAnswer(keyOfQuizItem.equals(ANSWER_INPUT))
                .build();
    }
}
