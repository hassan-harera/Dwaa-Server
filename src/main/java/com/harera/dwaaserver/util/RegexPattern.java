package com.harera.dwaaserver.util;

public final class RegexPattern {

    public static final String PERMITTED_URI_REGEX =
                    "(\\/token\\/invalidate|\\/token\\/refresh|\\/token\\/authenticate|\\/token\\/guest"
                                    + "|\\/reset-password\\/apply\\/|\\/reset-password\\/request\\/|\\/reset-password\\/validate\\/"
                                    + "|\\/patients\\/user\\/request|\\/patients\\/user\\/validate|\\/patients\\/user\\/photo|\\/patients\\/user"
                                    + "|\\/users\\/validate)";
    public static final String LANDLINE_REGEX = "[0-9]{7,11}";
    public static final String MOBILE_REGEX = "(?=(?:010|011|012|015)+(?:\\d{8})+).{11}";

    //email regex
    public static final String EMAIL_REGEX = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";

    public static final String USERNAME_REGEX = "^[A-Za-z]{1,15}[-_\\.]{0,1}[A-Za-z0-9_]{1,29}$";

    private RegexPattern() {

    }

}
