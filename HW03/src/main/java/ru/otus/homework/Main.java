package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import ru.otus.homework.config.AppProps;
import ru.otus.homework.service.RunnerQuests;

@SpringBootApplication
@EnableConfigurationProperties(AppProps.class)
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        RunnerQuests runnerQuests = context.getBean(RunnerQuests.class);
        runnerQuests.runQuest();
    }

}