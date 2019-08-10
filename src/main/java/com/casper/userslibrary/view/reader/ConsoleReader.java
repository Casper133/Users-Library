package com.casper.userslibrary.view.reader;

import com.casper.userslibrary.view.MessageReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader implements MessageReader {
    private BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String readMessage() {
        try {
            return bufferedReader.readLine().trim();
        } catch (IOException ignored) {
            return null;
        }
    }

    @Override
    public void stopReading() {
        try {
            bufferedReader.close();
        } catch (IOException ignored) {
        }
    }
}
