package com.casper.userslibrary.controller.command.wrapper;

import com.casper.userslibrary.controller.command.Command;
import com.casper.userslibrary.controller.command.CommandUtils;
import com.casper.userslibrary.model.entity.User;
import com.casper.userslibrary.model.exception.OperationFailedException;
import com.casper.userslibrary.model.repository.UserRepository;
import com.casper.userslibrary.view.MessageReader;
import com.casper.userslibrary.view.MessageWriter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EditUserCommand implements Command {
    private final MessageWriter messageWriter;

    private final MessageReader messageReader;

    private final UserRepository userRepository;

    private final CommandUtils commandUtils;


    private boolean editUser(User user) {
        switch (messageReader.readMessage()) {
            case "1":
                messageWriter.writeMessage("Введите новое имя:");
                user.setFirstName(messageReader.readMessage());
                break;
            case "2":
                messageWriter.writeMessage("Введите новую фамилию:");
                user.setLastName(messageReader.readMessage());
                break;
            case "3":
                messageWriter.writeMessage("Введите новый e-mail:");
                user.setEmail(commandUtils.readEmail());
                break;
            case "4":
                messageWriter.writeMessage("Введите новые роли пользователя (максимум 3, разделены символом \"|\"):");
                user.setRoles(commandUtils.getArrayUserElement(
                        "Список ролей некорректный. Попробуйте ещё раз:"));
                break;
            case "5":
                messageWriter.writeMessage(
                        "Введите новые номера телефонов пользователя " +
                                "(максимум 3, разделены символом \"|\", формат: 375** *******):");

                String[] phoneNumbers = commandUtils.getArrayUserElement(
                        "Список телефонов некорректный. Попробуйте ещё раз:");

                commandUtils.validatePhoneNumbers(phoneNumbers);
                break;
            default:
                messageWriter.writeMessage("Неизвестный номер поля.");
                return false;
        }

        return true;
    }

    @Override
    public void execute() {
        try {
            commandUtils.outputAllUsers();
            messageWriter.writeMessage("Введите id редактируемого пользователя:");
            User user = commandUtils.chooseUser();

            messageWriter.writeMessage("Введите номер редактируемого поля " +
                    "(1 - имя, 2 - фамилия, 3 - e-mail, 4 - роли, 5 - номера телефонов):");

            if (editUser(user)) {
                userRepository.update(user);
                messageWriter.writeMessage("Пользователь (" + user.getId() + " id) отредактирован.\n");
            }
        } catch (OperationFailedException e) {
            messageWriter.writeMessage(e.getMessage() + "\n");
        }
    }
}
