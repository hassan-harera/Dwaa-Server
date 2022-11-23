package com.harera.hayatserver.util.time

import java.time.ZonedDateTime

object ZonedDateTimeUtils {

    const val FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"

    fun isValidZonedDateTime(string: String): Boolean {
        try {
            ZonedDateTime.parse(string)
            return true
        } catch (e: Exception) {
            return false
        }
    }

}