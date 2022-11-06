package com.harera.dwaaserver.model.user


data class LoginRequest(
    val subject: String? = null,
    val password: String? = null
)