package com.harera.dwaaserver.common.exception

import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import java.lang.NullPointerException

class NullDateException : NullPointerException() {

    fun getResponseEntity(): ResponseEntity<String> {
        return ResponseEntity.badRequest().body("invalid date")
    }
}

