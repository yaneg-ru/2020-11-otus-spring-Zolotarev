package ru.otus.homework.model.factory;

import org.springframework.context.MessageSource;

import ru.otus.homework.config.AppProps;
import ru.otus.homework.model.Question;

public class QuestionFactory implements QuizItemFactory {

    private final AppProps appProps;
    private final MessageSource messageSource;

    public QuestionFactory(AppProps appProps, MessageSource messageSource) {
        this.appProps = appProps;
        this.messageSource = messageSource;
    }

    @Override
    public Question createQuizItem(String keyOfQuizItem, String lineFromSource) {
        return Question.builder()
                .textOfQuestion(
                        messageSource.getMessage(
                                lineFromSource.substring(keyOfQuizItem.length()),
                                null,
                                appProps.getLocale()
                        )
                )
                .build();
    }
}
