package com.emissa.apps.greenflag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class handles passwords validation on account creation
 * A password should have a minimum of 8 characters and contain at least one number,
 * one uppercase and one lower case letter and special characters
 */
public class PasswordValidator {

    /**
     * A password should have a minimum of 8 characters and contain at least one number,
     * one uppercase and one lower case letter and special characters
     */
    public static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$";;
    public static final Pattern passParttern = Pattern.compile(PASSWORD_REGEX);

    public static boolean isPassValid(final String password) {
        Matcher passMatcher = passParttern.matcher(password);
        return passMatcher.matches();
    }
}
