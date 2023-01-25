package com.harera.hayat.util

object StringRegexUtils {

    @JvmStatic
    fun isUsername(subject: String): Boolean {
        return subject.matches(RegexPattern.USERNAME_REGEX.toRegex())
    }

    @JvmStatic
    fun isEmail(subject: String): Boolean {
        return subject.matches(RegexPattern.EMAIL_REGEX.toRegex())
    }

    @JvmStatic
    fun isPhoneNumber(subject: String): Boolean {
        return subject.matches(RegexPattern.MOBILE_REGEX.toRegex())
    }

    @JvmStatic
    fun isValidPassword(Password: String): Boolean {
        return Password.matches(RegexPattern.PASSWORD_REGEX.toRegex())
    }
}