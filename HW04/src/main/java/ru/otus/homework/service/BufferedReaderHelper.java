package ru.otus.homework.service;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderHelper {
    BufferedReader getBufferedReaderFromFile() throws IOException;
    BufferedReader getBufferedReaderFromSystemIn();
    void closeBufferedReader(BufferedReader reader) throws IOException;

}
