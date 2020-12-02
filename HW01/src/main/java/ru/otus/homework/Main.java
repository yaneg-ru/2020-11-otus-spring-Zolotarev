package ru.otus.homework;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.otus.homework.model.Question;
import ru.otus.homework.service.ReaderQuests;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        ReaderQuests readerQuests = context.getBean(ReaderQuests.class);
        List<Question> questionList = readerQuests.readQuestions();
        questionList.forEach(System.out::println);
        context.close();
    }

}
