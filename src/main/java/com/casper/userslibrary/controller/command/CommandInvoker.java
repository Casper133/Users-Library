package com.casper.userslibrary.controller.command;

import com.casper.userslibrary.controller.command.wrapper.*;
import com.casper.userslibrary.model.repository.UserRepository;
import com.casper.userslibrary.model.repository.wrapper.UserFileRepository;
import com.casper.userslibrary.model.validator.Validator;
import com.casper.userslibrary.model.validator.wrapper.EmailValidator;
import com.casper.userslibrary.model.validator.wrapper.PhoneNumberValidator;
import com.casper.userslibrary.view.MessageReader;
import com.casper.userslibrary.view.MessageWriter;

public class CommandInvoker {
    private boolean[] exitFlag;

    private final Command newUserCommand;

    private final Command editUserCommand;

    private final Command deleteUserCommand;

    private final Command allUsersCommand;

    private final Command helpCommand;

    private final Command exitCommand;

    private final Command unknownCommand;


    public CommandInvoker(boolean[] exitFlag, MessageWriter messageWriter, MessageReader messageReader) {
        this.exitFlag = exitFlag;
        Validator emailValidator = new EmailValidator();
        Validator phoneNumberValidator = new PhoneNumberValidator();
        UserRepository userRepository = new UserFileRepository();

        CommandUtils commandUtils =
                new CommandUtils(messageWriter, messageReader, emailValidator, phoneNumberValidator, userRepository);

        newUserCommand = new NewUserCommand(messageWriter, messageReader, userRepository, commandUtils);
        editUserCommand = new EditUserCommand(messageWriter, messageReader, userRepository, commandUtils);
        deleteUserCommand = new DeleteUserCommand(messageWriter, userRepository, commandUtils);
        allUsersCommand = new AllUsersCommand(messageWriter, commandUtils);
        helpCommand = new HelpCommand(messageWriter);
        exitCommand = new ExitCommand(exitFlag, messageWriter);
        unknownCommand = new UnknownCommand(messageWriter);
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
            case "/exit":
                exitCommand.execute();
                break;
            default:
                unknownCommand.execute();
                helpCommand.execute();
                break;
        }
    }

    public boolean[] getExitFlag() {
        return exitFlag;
    }
}
