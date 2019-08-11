package com.casper.userslibrary.controller.command.wrapper;

import com.casper.userslibrary.controller.command.Command;
import com.casper.userslibrary.model.entity.User;
import com.casper.userslibrary.model.exception.OperationFailedException;
import com.casper.userslibrary.model.repository.UserRepository;
import com.casper.userslibrary.model.validator.Validator;
import com.casper.userslibrary.view.MessageReader;
import com.casper.userslibrary.view.MessageWriter;
import lombok.AllArgsConstructor;

import java.util.regex.Pattern;

@AllArgsConstructor
public class NewUserCommand implements Command {
    private final MessageWriter messageWriter;

    private final MessageReader messageReader;

    private final Validator emailValidator;

    private final Validator phoneNumberValidator;

    private final UserRepository userRepository;


    private String[] getArrayUserElement(String errorMessage) {
        Pattern splitPattern = Pattern.compile("\\s");
        boolean valid = false;
        String rawElement;
        String[] elements = new String[0];

        while (!valid) {
            rawElement = messageReader.readMessage();
            elements = splitPattern.split(rawElement);

            if (elements.length >= 1 && elements.length <= 3) {
                valid = true;
            } else {
                messageWriter.writeMessage(errorMessage);
            }
        }

        return elements;
    }

    private String readEmail() {
        boolean emailValid = false;
        String email = null;

        while (!emailValid) {
            email = messageReader.readMessage();
            emailValid = emailValidator.isValid(email);
            if (!emailValid) {
                messageWriter.writeMessage("E-mail некорректный. Попробуйте ещё раз:");
            }
        }

        return email;
    }

    private void validatePhoneNumbers(String[] phoneNumbers) {
        for (byte i = 0; i <= phoneNumbers.length - 1; i++) {
            while (!phoneNumberValidator.isValid(phoneNumbers[i])) {
                messageWriter.writeMessage((i + 1) +
                        " номер телефона некорректный (формат: 375** *******). Попробуйте ещё раз:");
                phoneNumbers[i] = messageReader.readMessage();
            }
        }
    }

    @Override
    public void execute() {
        messageWriter.writeMessage("Процесс создания нового пользователя запущен.");
        messageWriter.writeMessage("Введите имя:");
        String firstName = messageReader.readMessage();

        messageWriter.writeMessage("Введите фамилию:");
        String lastName = messageReader.readMessage();

        messageWriter.writeMessage("Введите e-mail:");
        String email = readEmail();

        messageWriter.writeMessage("Введите роли пользователя (максимум 3, разделены пробелом):");
        String[] roles = getArrayUserElement(
                "Список ролей некорректный. Попробуйте ещё раз:");

        messageWriter.writeMessage(
                "Введите телефоны пользователя (максимум 3, разделены пробелом, формат: 375** *******):");
        String[] phoneNumbers = getArrayUserElement(
                "Список телефонов некорректный. Попробуйте ещё раз:");

        validatePhoneNumbers(phoneNumbers);

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

        messageWriter.stop();
        messageReader.stop();
    }
}
