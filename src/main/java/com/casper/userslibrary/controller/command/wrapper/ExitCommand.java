package com.casper.userslibrary.controller.command.wrapper;

import com.casper.userslibrary.controller.command.Command;
import com.casper.userslibrary.view.MessageWriter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExitCommand implements Command {
    private boolean[] exitFlag;

    private final MessageWriter messageWriter;


    @Override
    public void execute() {
        exitFlag[0] = true;
        messageWriter.writeMessage("Процесс выхода из приложения запущен. Удачного дня!");
    }
}
