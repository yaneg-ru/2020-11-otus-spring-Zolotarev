package ru.otus.homework;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import lombok.extern.slf4j.Slf4j;
import ru.otus.homework.config.AppProps;
import ru.otus.homework.service.RunnerQuests;

@SpringBootApplication
@EnableConfigurationProperties(AppProps.class)
@Slf4j
public class Main {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        RunnerQuests runnerQuests = context.getBean(RunnerQuests.class);
        try {
            runnerQuests.runQuest();
        }
        catch (IOException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

}