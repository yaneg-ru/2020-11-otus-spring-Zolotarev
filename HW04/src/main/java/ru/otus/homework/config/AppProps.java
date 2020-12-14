package ru.otus.homework.config;

import java.util.Locale;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "questions")
@Data
public class AppProps {
    private String pathFile;
    private int minimumNumberOfCorrectAnswers;
    private Locale locale;
}
