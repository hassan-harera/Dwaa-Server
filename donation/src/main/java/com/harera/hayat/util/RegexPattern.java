package com.harera.hayat.util;

public final class RegexPattern {

    public static final String MOBILE_REGEX = "(?=(?:010|011|012|015)+(?:\\d{8})+).{11}";

    //email regex
    public static final String EMAIL_REGEX = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";

    public static final String USERNAME_REGEX = "^[A-Za-z]{1,15}[-_\\.]{0,1}[A-Za-z0-9_]{1,29}$";

    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

    private RegexPattern() {

    }

}
