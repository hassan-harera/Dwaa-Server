package com.harera.hayatserver.model.user


data class LoginRequest(
    val subject: String? = null,
    val password: String? = null
)