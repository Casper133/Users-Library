package com.casper.userslibrary.model.validator.wrapper;

import com.casper.userslibrary.model.validator.Validator;

import java.util.regex.Pattern;

public class EmailValidator implements Validator {
    @Override
    public boolean isValid(String email) {
        return email != null && Pattern.matches("\\w+@\\w+\\.\\w+", email);
    }
}
