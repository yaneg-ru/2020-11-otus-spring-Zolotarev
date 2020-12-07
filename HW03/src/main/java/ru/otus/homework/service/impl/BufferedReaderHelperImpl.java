package ru.otus.homework.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import ru.otus.homework.config.AppProps;
import ru.otus.homework.service.BufferedReaderHelper;

@Service
public class BufferedReaderHelperImpl implements BufferedReaderHelper {

    private final AppProps appProps;
    private final ResourceLoader resourceLoader;

    public BufferedReaderHelperImpl(AppProps appProps, ResourceLoader resourceLoader) {
        this.appProps = appProps;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BufferedReader getBufferedReaderFromSystemIn() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public BufferedReader getBufferedReaderFromFile() {
        Resource resource = resourceLoader.getResource(appProps.getPathFile());
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        InputStreamReader streamReader =
                new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
        return new BufferedReader(streamReader);
    }

    @Override
    public void closeBufferedReader(BufferedReader reader) {
        try {
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
