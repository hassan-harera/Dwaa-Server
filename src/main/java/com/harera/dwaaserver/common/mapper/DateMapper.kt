package com.harera.dwaaserver.common.mapper

import com.harera.dwaaserver.common.utils.CustomPattern
import java.text.SimpleDateFormat
import java.util.*

object DateMapper {

    private const val DATE_FORMAT = "yyyy-MM-dd"

    fun map(date : String?) : Date? {
        return if (date.isNullOrBlank() or date!!.matches(CustomPattern.DATE_FORMAT.toRegex()).not()) {
            null
        } else {
            SimpleDateFormat(DATE_FORMAT).parse(date)
        }
    }
}