package com.harera.dwaaserver.service

import com.harera.dwaaserver.data.donationPostRequest
import com.harera.dwaaserver.data.uid
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class DonationPostServiceTest {

    @Autowired
    private lateinit var donationPostService: DonationPostService

    @Test
    fun `test to get donation post`() {
        donationPostService.insertDonationPost(donationPostRequest, uid)
    }
}