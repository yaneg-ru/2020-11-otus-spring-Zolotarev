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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import ru.otus.homework.model.Answer;
import ru.otus.homework.model.Question;

@SpringJUnitConfig(value = {RunnerQuestsImpl.class})
@TestPropertySource(properties = {"pathFileQuestions=questions.csv", "minimumNumberOfCorrectAnswers=3"})
class RunnerQuestsImplTest {

    @Autowired
    RunnerQuestsImpl runnerQuests;

    @MockBean
    BufferedReaderHelperImpl bufferedReaderHelper;

    @Mock
    BufferedReader bufferedReader;

    @Test
    void readQuestions_shouldReadOneQuestion_whenInFileOnlyOneQuestion() {
        try {
            when(bufferedReader.readLine())
                    .thenReturn("Question:Какого окраса был кот Леопольд?")
                    .thenReturn("AnswerTrue:Рыжий")
                    .thenReturn("AnswerFalse:Серый")
                    .thenReturn("AnswerFalse:Черный")
                    .thenReturn("AnswerFalse:Белый")
                    .thenReturn(null);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        when(bufferedReaderHelper.getBufferedReader()).thenReturn(bufferedReader);
        List<Answer> expectedAnswers = new ArrayList<>();
        expectedAnswers.add(Answer.builder()
                            .isRightAnswer(true)
                            .textOfAnswer("Рыжий")
                            .build()
        );
        expectedAnswers.add(Answer.builder()
                            .isRightAnswer(false)
                            .textOfAnswer("Серый")
                            .build()
        );
        expectedAnswers.add(Answer.builder()
                            .isRightAnswer(false)
                            .textOfAnswer("Черный")
                            .build()
        );
        expectedAnswers.add(Answer.builder()
                            .isRightAnswer(false)
                            .textOfAnswer("Белый")
                            .build()
        );
        Question expectedQuestion = Question.builder()
                .textOfQuestion("Какого окраса был кот Леопольд?")
                .answers(expectedAnswers)
                .build();
        List<Question> actualQuestions = runnerQuests.readQuestions();
        assertEquals(expectedQuestion.getTextOfQuestion(), actualQuestions.get(0).getTextOfQuestion());
        for (int i = 0; i < 4; i++) {
                assertEquals(expectedAnswers.get(i), actualQuestions.get(0).getAnswers().get(i));
        }
    }
}