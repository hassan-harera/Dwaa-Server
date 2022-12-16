package com.harera.hayat.util;

public class HayatStringUtils {

    public static boolean isValidMobile(String mobile) {
        return mobile.matches("[0-9]+") && mobile.length() == 10;
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    public static boolean isValidName(String name) {
        return name.length() >= 3;
    }

    public static boolean isValidEmail(String email) {
        return email.matches("^(.+)@(.+)$");
    }
}
