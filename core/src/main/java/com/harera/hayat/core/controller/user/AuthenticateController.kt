package com.harera.hayat.core.controller.user

import com.harera.hayat.model.user.auth.*
import com.harera.hayat.service.user.UserService
import lombok.extern.log4j.Log4j2
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Log4j2
@RestController
@RequestMapping("/api/v1")
class AuthenticateController(
    private val userService: UserService,
) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        return userService.login(loginRequest).let {
            ResponseEntity.ok(it)
        }
    }

    @PostMapping("/oauth/login")
    fun login(@RequestBody loginRequest: OAuthLoginRequest): ResponseEntity<LoginResponse> {
        return userService.login(loginRequest).let { ResponseEntity.ok(it) }
    }


    @PostMapping("/signup")
    fun signup(@RequestBody signupRequest: SignupRequest): ResponseEntity<SignupResponse> {
        return userService.signup(signupRequest).let {
            ResponseEntity.ok(it)
        }
    }
}