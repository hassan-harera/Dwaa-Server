package com.harera.dwaaserver.repository

import com.harera.dwaaserver.data.*
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class UserEntityRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `test to insert user`() {
        /**
         * This test will fail because the user is already in the database
         *
         */
        userRepository.deleteById(uid)
        val optional = userRepository.saveAndFlush(userEntity)
        Assertions.assertThat(optional).isNotNull
    }
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