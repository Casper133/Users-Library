package com.casper.userslibrary.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class User {
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private List<String> roles;

    private List<String> phoneNumbers;
}
