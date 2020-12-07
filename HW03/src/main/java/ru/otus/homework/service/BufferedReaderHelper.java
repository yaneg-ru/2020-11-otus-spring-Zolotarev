package ru.otus.homework.service;

import java.io.BufferedReader;

public interface BufferedReaderHelper {
    BufferedReader getBufferedReaderFromFile();
    BufferedReader getBufferedReaderFromSystemIn();
    void closeBufferedReader(BufferedReader reader);

}
