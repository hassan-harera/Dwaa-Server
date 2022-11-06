package com.harera.dwaaserver.repository

import com.harera.dwaaserver.data.PACKAGING_ID
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ParamRepositoryTest {

    @Autowired
    private lateinit var packagingRepository: PackagingRepository

    @Test
    fun `test packaging repository`() {
        packagingRepository.allPackaging.forEach {
            println(it)
        }
    }

    @Test
    fun `test to get packaging type with id`() {
        val packagingEntityOptional = packagingRepository.getPackaging(PACKAGING_ID)
        Assertions.assertThat(packagingEntityOptional.isEmpty).isFalse
    }
}