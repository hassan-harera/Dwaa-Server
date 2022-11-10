package com.harera.hayatserver.service

import com.harera.hayatserver.data.PACKAGING_ID
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.jvm.optionals.getOrNull

@OptIn(ExperimentalStdlibApi::class)
@SpringBootTest
class ParamServiceTest {

    @Autowired
    private lateinit var packagingService: PackagingService

    @Test
    fun `test packaging repository`() {
        packagingService.packagingList.blockOptional().getOrNull()?.forEach {
            println(it)
        }
    }

    @Test
    fun `test to get packaging type with id`() {
        val packagingEntityOptional = packagingService.getPackaging(PACKAGING_ID).blockOptional()
        Assertions.assertThat(packagingEntityOptional.isEmpty).isFalse
        println(packagingEntityOptional.getOrNull())
    }
}