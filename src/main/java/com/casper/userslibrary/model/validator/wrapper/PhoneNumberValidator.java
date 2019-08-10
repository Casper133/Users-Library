package com.casper.userslibrary.model.validator.wrapper;

import com.casper.userslibrary.model.validator.Validator;

import java.util.regex.Pattern;

public class PhoneNumberValidator implements Validator {
    @Override
    public boolean isValid(String phoneNumber) {
        return Pattern.matches("375\\d{2} \\d{7}", phoneNumber);
    }
}
