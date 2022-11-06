package com.harera.dwaaserver.common.exception

import org.springframework.http.ResponseEntity

class NullDateException : NullPointerException() {

    fun getResponseEntity(): ResponseEntity<String> {
        return ResponseEntity.badRequest().body("invalid date")
    }
}

