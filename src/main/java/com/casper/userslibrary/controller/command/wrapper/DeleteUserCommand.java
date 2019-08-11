package com.casper.userslibrary.controller.command.wrapper;

import com.casper.userslibrary.controller.command.Command;
import com.casper.userslibrary.controller.command.CommandUtils;
import com.casper.userslibrary.model.entity.User;
import com.casper.userslibrary.model.exception.OperationFailedException;
import com.casper.userslibrary.model.repository.UserRepository;
import com.casper.userslibrary.view.MessageWriter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteUserCommand implements Command {
    private final MessageWriter messageWriter;

    private final UserRepository userRepository;

    private final CommandUtils commandUtils;


    @Override
    public void execute() {
        try {
            commandUtils.outputAllUsers();

            messageWriter.writeMessage("Введите id удаляемого пользователя:");
            User user = commandUtils.chooseUser();

            userRepository.delete(user);
            messageWriter.writeMessage("Пользователь (" + user.getId() + " id) удалён.\n");
        } catch (OperationFailedException e) {
            messageWriter.writeMessage(e.getMessage() + "\n");
        }
    }
}
