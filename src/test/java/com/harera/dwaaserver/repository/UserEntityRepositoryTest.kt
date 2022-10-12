package com.harera.dwaaserver.repository

import com.harera.dwaaserver.data.email
import com.harera.dwaaserver.data.phoneNumber
import com.harera.dwaaserver.data.uid
import com.harera.dwaaserver.data.username
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class UserEntityRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `test to get user by username`() {
        val optional = userRepository.getUserWithUsername(username)
        Assertions.assertThat(optional.isPresent).isTrue()
    }

    @Test
    fun `test to get user by uid`() {
        val optional = userRepository.getUserWithUid(uid)
        Assertions.assertThat(optional.isPresent).isTrue
    }

    @Test
    fun `test to get user by email`() {
        val optional = userRepository.getUserWithEmail(email)
        Assertions.assertThat(optional.isPresent).isTrue
    }

    @Test
    fun `test to get user by phone number`() {
        val optional = userRepository.getUserWithPhoneNumber(phoneNumber)
        Assertions.assertThat(optional.isPresent).isTrue
    }
}