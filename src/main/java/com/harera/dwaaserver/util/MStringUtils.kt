package com.harera.dwaaserver.util

object MStringUtils {


    fun isUsername(subject: String): Boolean {
        return subject.matches(RegexPattern.USERNAME_REGEX.toRegex())
    }

    fun isEmail(subject: String): Boolean {
        return subject.matches(RegexPattern.EMAIL_REGEX.toRegex())
    }

    fun isPhoneNumber(subject: String): Boolean {
        return subject.matches(RegexPattern.MOBILE_REGEX.toRegex())
    }
}