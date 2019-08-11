package com.casper.userslibrary.controller.command.wrapper;

import com.casper.userslibrary.controller.command.Command;
import com.casper.userslibrary.view.MessageWriter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnknownCommand implements Command {
    private final MessageWriter messageWriter;

    @Override
    public void execute() {
        messageWriter.writeMessage("Команда не найдена.");
    }
}
