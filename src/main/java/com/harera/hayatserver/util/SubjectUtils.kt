package com.harera.hayatserver.util

object SubjectUtils {

    fun getSubject(subject: String): Subject {
        return if (StringRegexUtils.isPhoneNumber(subject)) {
            Subject.PhoneNumber(subject)
        } else if (StringRegexUtils.isEmail(subject)) {
            Subject.PhoneNumber(subject)
        } else if (StringRegexUtils.isUsername(subject)){
            Subject.PhoneNumber(subject)
        } else {
            Subject.Other
        }
    }
}