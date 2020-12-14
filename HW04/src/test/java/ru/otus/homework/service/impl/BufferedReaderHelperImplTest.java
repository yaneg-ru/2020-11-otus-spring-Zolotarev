package ru.otus.homework.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;

import ru.otus.homework.config.AppProps;
import ru.otus.homework.model.Answer;
import ru.otus.homework.model.Question;

@SpringBootTest
class BufferedReaderHelperImplTest {
    @Autowired
    RunnerQuestsImpl runnerQuests;

    @Autowired
    MessageSource messageSource;

    @Autowired
    AppProps appProps;

    @MockBean
    BufferedReaderHelperImpl bufferedReaderHelper;

    @Mock
    BufferedReader bufferedReader;

    @Test
    void readQuestions_shouldReadOneQuestion_whenInFileOnlyOneQuestion() throws IOException {
        try {
            when(bufferedReader.readLine())
                    .thenReturn("Question:question1")
                    .thenReturn("AnswerTrue:answerTrue11")
                    .thenReturn("AnswerFalse:answerFalse11")
                    .thenReturn("AnswerFalse:answerFalse12")
                    .thenReturn("AnswerFalse:answerFalse13")
                    .thenReturn(null);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        when(bufferedReaderHelper.getBufferedReaderFromFile()).thenReturn(bufferedReader);
        List<Answer> expectedAnswers = new ArrayList<>();
        expectedAnswers.add(Answer.builder()
                                    .isRightAnswer(true)
                                    .textOfAnswer(
                                            messageSource.getMessage(
                                                    "answerTrue11",
                                                    null,
                                                    appProps.getLocale()
                                            )
                                    )
                                    .build()
        );
        expectedAnswers.add(Answer.builder()
                                    .isRightAnswer(false)
                                    .textOfAnswer(
                                            messageSource.getMessage(
                                                    "answerFalse11",
                                                    null,
                                                    appProps.getLocale()
                                            )
                                    )
                                    .build()
        );
        expectedAnswers.add(Answer.builder()
                                    .isRightAnswer(false)
                                    .textOfAnswer(
                                            messageSource.getMessage(
                                                    "answerFalse12",
                                                    null,
                                                    appProps.getLocale()
                                            )
                                    )
                                    .build()
        );
        expectedAnswers.add(Answer.builder()
                                    .isRightAnswer(false)
                                    .textOfAnswer(
                                            messageSource.getMessage(
                                                    "answerFalse13",
                                                    null,
                                                    appProps.getLocale()
                                            )
                                    )
                                    .build()
        );
        Question expectedQuestion = Question.builder()
                .textOfQuestion(
                        messageSource.getMessage(
                                "question1",
                                null,
                                appProps.getLocale()
                        )
                )
                .answers(expectedAnswers)
                .build();
        List<Question> actualQuestions = runnerQuests.readQuestions();
        assertEquals(expectedQuestion.getTextOfQuestion(), actualQuestions.get(0).getTextOfQuestion());
        for (int i = 0; i < 4; i++) {
            assertEquals(expectedAnswers.get(i), actualQuestions.get(0).getAnswers().get(i));
        }
    }
}