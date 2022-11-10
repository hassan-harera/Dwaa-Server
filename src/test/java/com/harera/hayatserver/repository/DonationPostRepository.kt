package com.harera.hayatserver.repository

import com.harera.hayatserver.data.donationPostEntity
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class DonationPostRepository {

    @Autowired
    private lateinit var donationPostEntityRepository: DonationPostEntityRepository

    @Test
    fun `test to insert donation post`() {
        val save = donationPostEntityRepository.save(donationPostEntity)
        Assertions.assertThat(save).isNotNull
    }

    @Test
    fun `test to get donation posts with date filter`() {
        val save = donationPostEntityRepository.save(donationPostEntity)
        Assertions.assertThat(save).isNotNull
    }
}