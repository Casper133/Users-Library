package com.casper.userslibrary.controller;

import com.casper.userslibrary.controller.command.CommandInvoker;
import com.casper.userslibrary.view.MessageReader;
import com.casper.userslibrary.view.MessageWriter;
import com.casper.userslibrary.view.reader.ConsoleReader;
import com.casper.userslibrary.view.writer.ConsoleWriter;

public class AppController {
    public static void start() {
        boolean[] exitFlag = {false};
        MessageWriter messageWriter = new ConsoleWriter();
        MessageReader messageReader = new ConsoleReader();

        messageWriter.writeMessage("Добро пожаловать! Для просмотра доступных команд введите /help.");
        CommandInvoker commandInvoker = new CommandInvoker(exitFlag, messageWriter, messageReader);

        while (!commandInvoker.getExitFlag()[0]) {
            commandInvoker.handle(messageReader.readMessage());
        }

        messageWriter.stop();
        messageReader.stop();
    }
}
