package com.harera.dwaaserver.util

object StringRegexUtils {

    fun isUsername(subject: String): Boolean {
        return subject.matches(RegexPattern.USERNAME_REGEX.toRegex())
    }

    fun isEmail(subject: String): Boolean {
        return subject.matches(RegexPattern.EMAIL_REGEX.toRegex())
    }

    fun isPhoneNumber(subject: String): Boolean {
        return subject.matches(RegexPattern.MOBILE_REGEX.toRegex())
    }

    @JvmStatic
    fun isValidPassword(Password: String): Boolean {
        return Password.matches(RegexPattern.PASSWORD_REGEX.toRegex())
    }
}