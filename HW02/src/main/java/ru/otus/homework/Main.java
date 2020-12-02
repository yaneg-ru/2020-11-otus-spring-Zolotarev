package ru.otus.homework;

import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.otus.homework.app.AppConfig;
import ru.otus.homework.service.RunnerQuests;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        RunnerQuests runnerQuests = context.getBean(RunnerQuests.class);
        try {
            runnerQuests.runQuest();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
