package com.harera.dwaaserver.security

import com.harera.dwaaserver.data.uid
import com.harera.dwaaserver.data.username
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class JwtServiceTest {

    @Autowired
    private lateinit var jwtService: JwtService

    @Test
    fun `test to generate token`() {
        val token = jwtService.generateUserToken(CustomUserDetails(uid.toString(), "password"))
        println(token)
    }
}