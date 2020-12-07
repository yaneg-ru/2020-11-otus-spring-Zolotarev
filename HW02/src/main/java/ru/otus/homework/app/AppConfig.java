package ru.otus.homework.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("ru.otus.homework")
@PropertySource("classpath:applicatoin.properties")
public class AppConfig {
}
