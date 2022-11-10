package com.harera.hayatserver.serializer

import com.fasterxml.jackson.databind.ObjectMapper
import com.harera.hayatserver.data.donationPostRequest
import org.junit.jupiter.api.Test

class JsonSerializer {

    @Test
    fun `test to serialize donation post request`() {
        println(ObjectMapper().writeValueAsString(donationPostRequest))
    }
}