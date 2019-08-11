package com.casper.userslibrary.controller.command;

import com.casper.userslibrary.model.entity.User;
import com.casper.userslibrary.model.exception.OperationFailedException;
import com.casper.userslibrary.model.repository.UserRepository;
import com.casper.userslibrary.model.validator.Validator;
import com.casper.userslibrary.view.MessageReader;
import com.casper.userslibrary.view.MessageWriter;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.regex.Pattern;

@AllArgsConstructor
public class CommandUtils {
    private final MessageWriter messageWriter;

    private final MessageReader messageReader;

    private final Validator emailValidator;

    private final Validator phoneNumberValidator;

    private final UserRepository userRepository;


    public String readEmail() {
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

    public String[] getArrayUserElement(String errorMessage) {
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

    public void validatePhoneNumbers(String[] phoneNumbers) {
        for (byte i = 0; i <= phoneNumbers.length - 1; i++) {
            while (!phoneNumberValidator.isValid(phoneNumbers[i])) {
                messageWriter.writeMessage((i + 1) +
                        " номер телефона некорректный (формат: 375** *******). Попробуйте ещё раз:");
                phoneNumbers[i] = messageReader.readMessage();
            }
        }
    }

    public void outputAllUsers() throws OperationFailedException {
        List<User> users = userRepository.getAll();
        messageWriter.writeMessage("Текущий список пользователей:");
        for (User user : users) {
            messageWriter.writeMessage(user.toString());
        }
    }

    public User chooseUser() throws OperationFailedException {
        boolean idValid = false;
        long userId = -1;
        while (!idValid) {
            try {
                userId = Long.parseLong(messageReader.readMessage());
                idValid = true;
            } catch (NumberFormatException e) {
                messageWriter.writeMessage("Некорректный ввод id. Попробуйте ещё раз:");
            }
        }

        User user = userRepository.getById(userId);
        messageWriter.writeMessage("Выбранный пользователь:");
        messageWriter.writeMessage(user.toString());

        return user;
    }
}
