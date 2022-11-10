package com.harera.dwaaserver.util

object ErrorCode {

    const val MANDATORY_SUBJECT = "subject_001"
    const val MANDATORY_UID = "uid_001"
    const val MANDATORY_FIRST_NAME = "first_name_001"
    const val MANDATORY_LAST_NAME = "last_name_001"
    const val MANDATORY_TOKEN = "token_001"
    const val MANDATORY_USER_NAME = "username_001"
    const val UNIQUE_USER_NAME = "username_002"
    const val MANDATORY_PASSWORD = "password_001"

    const val INCORRECT_USERNAME_OR_PASSWORD = "login_001"
    const val INVALID_TOKEN = "signup_001"
    const val INCORRECT_USERNAME_FORMAT = "login_002"

    const val UNIQUE_EMAIL = "email_001"
    const val UNIQUE_USER_MOBILE = "mobile_001"
    const val FORMAT_USER_NAME_MINIMUM = "username_002"
    const val FORMAT_USER_NAME_LENGTH = "username_003"
    const val FORMAT_USER_NAME_INVALID_CHARS = "username_004"
    const val FORMAT_USER_MOBILE = "mobile_002"
    const val MANDATORY_USER_MOBILE = "mobile_006"
    const val FORMAT_USER_PASSWORD = "password_002"
    const val UNIQUE_CLINIC_MOBILE = "mobile_003"
    const val FORMAT_CLINIC_MOBILE = "mobile_004"
}