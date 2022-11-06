package com.harera.dwaaserver.common.mapper

import com.harera.dwaaserver.data.invalidDate
import com.harera.dwaaserver.data.validDate
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DateMapperTest {

    @Test
    fun `test to map date`() {
        DateMapper.map(validDate).let {
            Assertions.assertThat(it).isNotNull
        }
    }

    @Test
    fun `map invalid date should be null`() {
        DateMapper.map(invalidDate).let {
            Assertions.assertThat(it).isNull()
        }
    }

    @Test
    fun `null date should fail`() {
        DateMapper.map(invalidDate).let {
            Assertions.assertThat(it).isNull()
        }
    }
}