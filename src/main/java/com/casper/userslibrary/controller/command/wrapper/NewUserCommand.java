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
public class NewUserCommand implements Command {
    private final MessageWriter messageWriter;

    private final MessageReader messageReader;

    private final UserRepository userRepository;

    private final CommandUtils commandUtils;


    @Override
    public void execute() {
        messageWriter.writeMessage("Процесс создания нового пользователя запущен.");
        messageWriter.writeMessage("Введите имя:");
        String firstName = messageReader.readMessage();

        messageWriter.writeMessage("Введите фамилию:");
        String lastName = messageReader.readMessage();

        messageWriter.writeMessage("Введите e-mail:");
        String email = commandUtils.readEmail();

        messageWriter.writeMessage("Введите роли пользователя (максимум 3, разделены пробелом):");
        String[] roles = commandUtils.getArrayUserElement(
                "Список ролей некорректный. Попробуйте ещё раз:");

        messageWriter.writeMessage(
                "Введите номера телефонов пользователя (максимум 3, разделены пробелом, формат: 375** *******):");
        String[] phoneNumbers = commandUtils.getArrayUserElement(
                "Список телефонов некорректный. Попробуйте ещё раз:");

        commandUtils.validatePhoneNumbers(phoneNumbers);

        User user = User
                .builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .roles(roles)
                    .phoneNumbers(phoneNumbers)
                .build();

        try {
            userRepository.save(user);
            messageWriter.writeMessage("Новый пользователь успешно сохранён.");
        } catch (OperationFailedException e) {
            messageWriter.writeMessage(e.getMessage());
        }
    }
}
