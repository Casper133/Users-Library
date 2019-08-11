package com.casper.userslibrary.controller.command.wrapper;

import com.casper.userslibrary.controller.command.Command;
import com.casper.userslibrary.controller.command.CommandUtils;
import com.casper.userslibrary.model.exception.OperationFailedException;
import com.casper.userslibrary.view.MessageWriter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AllUsersCommand implements Command {
    private final MessageWriter messageWriter;

    private final CommandUtils commandUtils;


    @Override
    public void execute() {
        try {
            commandUtils.outputAllUsers();
        } catch (OperationFailedException e) {
            messageWriter.writeMessage(e.getMessage() + "\n");
        }
    }
}
