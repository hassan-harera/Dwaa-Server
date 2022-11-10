package com.harera.hayatserver.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class StringRegexUtilsTest {

//    test password regex
    @Test
    fun validatePassword_withValidPassword_returnsTrue() {
        val password = "Password@123"
        assertTrue(StringRegexUtils.isValidPassword(password))
    }

//    test password without special character
    @Test
    fun validatePassword_withPasswordWithoutSpecialCharacter_returnsFalse() {
        val password = "Password123"
        assertFalse(StringRegexUtils.isValidPassword(password))
    }

    //    test password without number
    @Test
    fun validatePassword_withPasswordWithoutNumber_returnsFalse() {
        val password = "Password@"
        assertFalse(StringRegexUtils.isValidPassword(password))
    }

    //    test password without uppercase
    @Test
    fun validatePassword_withPasswordWithoutUppercase_returnsFalse() {
        val password = "password@123"
        assertFalse(StringRegexUtils.isValidPassword(password))
    }
}