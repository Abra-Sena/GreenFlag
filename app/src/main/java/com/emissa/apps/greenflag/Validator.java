package com.emissa.apps.greenflag;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class handles passwords validation on account creation
 * A password should have a minimum of 8 characters and contain at least one number,
 * one uppercase and one lower case letter and special characters
 */
public class Validator {

    /**
     * A password should have a minimum of 8 characters and contain at least one number,
     * one uppercase and one lower case letter and special characters
     */
    public static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$";;
    public static final Pattern passPattern = Pattern.compile(PASSWORD_REGEX);

    public static boolean isPassValid(final String password) {
        Matcher passMatcher = passPattern.matcher(password);
        return passMatcher.matches();
    }

    // TODO: not working as intended
    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
