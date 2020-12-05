package ru.otus.homework.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.otus.homework.service.BufferedReaderHelper;

@Service
public class BufferedReaderHelperImpl implements BufferedReaderHelper {

    private BufferedReader reader;

    @Value("${pathFileQuestions}")
    private String pathFileQuestions;

    @Override
    public BufferedReader getBufferedReader() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(pathFileQuestions);
        InputStreamReader streamReader =
                new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
        return reader = new BufferedReader(streamReader);
    }

    @Override
    public void closeBufferedReader() {
        try {
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
