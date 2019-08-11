package com.casper.userslibrary.controller.command;

import com.casper.userslibrary.controller.command.wrapper.EditUserCommand;
import com.casper.userslibrary.controller.command.wrapper.NewUserCommand;
import com.casper.userslibrary.model.repository.UserRepository;
import com.casper.userslibrary.model.validator.Validator;
import com.casper.userslibrary.view.MessageReader;
import com.casper.userslibrary.view.MessageWriter;

public class CommandInvoker {
    private Command newUserCommand;

    private Command editUserCommand;

    private Command deleteUserCommand;

    private Command allUsersCommand;

    private Command helpCommand;

    private Command unknownCommand;


    public CommandInvoker(MessageWriter messageWriter, MessageReader messageReader,
                          Validator emailValidator, Validator phoneNumberValidator, UserRepository userRepository) {

        CommandUtils commandUtils =
                new CommandUtils(messageWriter, messageReader, emailValidator, phoneNumberValidator, userRepository);

        newUserCommand = new NewUserCommand(messageWriter, messageReader, userRepository, commandUtils);
        editUserCommand = new EditUserCommand(messageWriter, messageReader, userRepository, commandUtils);
    }

    public void handle(String message) {
        message = message.trim().toLowerCase();

        switch (message) {
            case "/new_user":
                newUserCommand.execute();
                break;
            case "/edit_user":
                editUserCommand.execute();
                break;
            case "/delete_user":
                deleteUserCommand.execute();
                break;
            case "/all_users":
                allUsersCommand.execute();
                break;
            case "/help":
                helpCommand.execute();
                break;
            default:
                unknownCommand.execute();
                helpCommand.execute();
                break;
        }
    }
}
