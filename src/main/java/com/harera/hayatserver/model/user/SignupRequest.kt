package com.harera.hayatserver.model.user

data class SignupRequest(
    val username: String?,
    val subject: String?,
    val password: String?,
    val token: String?,
    val firstName: String?,
    val lastName: String?,
)
