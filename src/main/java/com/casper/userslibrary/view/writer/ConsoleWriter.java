package com.casper.userslibrary.view.writer;

import com.casper.userslibrary.view.MessageWriter;

public class ConsoleWriter implements MessageWriter {
    @Override
    public void writeMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void stopWriting() {}
}
