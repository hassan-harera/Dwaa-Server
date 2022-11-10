package com.harera.hayatserver.security

import com.harera.hayatserver.data.uid
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