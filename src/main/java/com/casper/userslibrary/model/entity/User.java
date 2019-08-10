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
}
