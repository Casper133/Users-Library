package com.casper.userslibrary.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class User {
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String[] roles;

    private String[] phoneNumbers;

    @Override
    public String toString() {
        StringBuilder userText = new StringBuilder(
                "Id: " + id + "\nИмя: " + firstName + "\nФамилия: " + lastName + "\nE-mail: " + email + "\nРоли: | ");

        for (String role : roles) {
            userText.append(role).append(" | ");
        }

        userText.append("\nНомера телефонов: | ");

        for (String phoneNumber : phoneNumbers) {
            userText.append(phoneNumber).append(" | ");
        }

        return userText.append("\n").toString();
    }
}
