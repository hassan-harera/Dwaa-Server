package com.harera.hayatserver.controller.user

import com.harera.hayatserver.model.user.LoginRequest
import com.harera.hayatserver.model.user.LoginResponse
import com.harera.hayatserver.model.user.OAuthLoginRequest
import com.harera.hayatserver.service.user.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.extern.log4j.Log4j2
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Log4j2
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Authentication")
class AuthenticateController(
    private val userService: UserService,
) {

    @PostMapping("/login")
    @Operation(
        summary = "Login",
        description = "login with subject and password",
        tags = ["Authentication"],
        responses = [ApiResponse(responseCode = "200", description = "success|OK")]
    )
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        return userService.login(loginRequest).let {
            ResponseEntity.ok(it)
        }
    }

    @PostMapping("/login/oauth")
    fun login(@RequestBody loginRequest: OAuthLoginRequest): ResponseEntity<LoginResponse> {
        return userService.login(loginRequest).let { ResponseEntity.ok(it) }
    }
}