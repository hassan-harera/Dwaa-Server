package com.harera.hayatserver.util.time

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
internal class ZonedDateTimeUtilsTest {


    @Test
    fun `isValidZonedDateTime() returns true for valid ZonedDateTime`() {
        val zonedDateTime = "2020-01-01T00:00:00.000+00:00"
        assertTrue(ZonedDateTimeUtils.isValidZonedDateTime(zonedDateTime))
    }

    @Test
    fun `isValidZonedDateTime() returns false for invalid ZonedDateTime`() {
        val zonedDateTime = "2020-01-01T00:00:00.000"
        assertFalse(ZonedDateTimeUtils.isValidZonedDateTime(zonedDateTime))
    }
}