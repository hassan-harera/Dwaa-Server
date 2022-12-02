package com.harera.hayatserver.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class DonationPostServiceTest {

    @Autowired
    private lateinit var donationPostService: DonationPostService

    @Test
    fun `test to get donation post`() {
    }
}