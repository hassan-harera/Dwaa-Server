package com.harera.hayat.util;

public final class ErrorCode {

    public static final String MANDATORY_SUBJECT = "subject_001";

    public static final String MANDATORY_UID = "uid_001";

    public static final String MANDATORY_FIRST_NAME = "first_name_001";

    public static final String MANDATORY_LAST_NAME = "last_name_001";

    public static final String MANDATORY_TOKEN = "token_001";

    public static final String MANDATORY_USER_NAME = "username_001";

    public static final String UNIQUE_USER_NAME = "username_002";

    public static final String MANDATORY_PASSWORD = "password_001";

    public static final String INCORRECT_USERNAME_OR_PASSWORD = "login_001";

    public static final String INVALID_TOKEN = "signup_001";
    public static final String INCORRECT_USERNAME_FORMAT = "login_002";
    public static final String UNIQUE_EMAIL = "email_001";
    public static final String UNIQUE_USER_MOBILE = "mobile_001";

    public static final String FORMAT_USER_NAME_MINIMUM = "username_002";
    public static final String FORMAT_USER_NAME_LENGTH = "username_003";
    public static final String FORMAT_USER_NAME_INVALID_CHARS = "username_004";
    public static final String FORMAT_USER_MOBILE = "mobile_002";
    public static final String MANDATORY_MOBILE = "mobile_006";
    public static final String FORMAT_USER_PASSWORD = "password_002";
    public static final String UNIQUE_CLINIC_MOBILE = "mobile_003";
    public static final String FORMAT_CLINIC_MOBILE = "mobile_004";
    public static final String NOT_FOUND_CITY_ID = "city_001";
    public static final String NOT_FOUND_STATE_ID = "state_001";
    public static final String MANDATORY_MEDICINE_UNIT_ID = "medicine_unit_001";
    public static final String MANDATORY_MEDICINE_ID = "medicine_001";
    public static final String FORMAT_TITLE = "title_002";
    public static final String FORMAT_MEDICINE_EXPIRATION_DATE =
                    "medicine_expiration_002";
    public static String MANDATORY_CITY_ID = "city_002";
    public static String MANDATORY_EXPIRATION_DATE = "expiration_date_001";
    public static String FORMAT_EXPIRATION_DATE = "expiration_date_002";
    public static String FORMAT_UNIT = "unit_001";
    public static String FORMAT_AMOUNT = "amount_001";
    public static String MANDATORY_AMOUNT = "amount_002";
    public static String MANDATORY_COMMUNICATION = "communication_001";
    public static String MANDATORY_TITLE = "title_001";

    private ErrorCode() {
    }
}
