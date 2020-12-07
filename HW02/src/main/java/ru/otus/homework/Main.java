package ru.otus.homework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.otus.homework.app.AppConfig;
import ru.otus.homework.service.RunnerQuests;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        RunnerQuests runnerQuests = context.getBean(RunnerQuests.class);
        runnerQuests.runQuest();
    }
}
