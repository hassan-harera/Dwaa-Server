package com.harera.hayat.security

import io.jsonwebtoken.Claims
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.function.Function

interface AuthenticationManager {

    fun extractUsernameFromToken(token: String): String
    fun extractExpiration(token: String?): Date
    fun <T> extractClaim(token: String?, claimsResolver: Function<Claims, T>): T
    fun extractAllClaims(token: String?): Claims
    fun generateAdminToken(userDetails: UserDetails): String
    fun generateUserToken(userDetails: UserDetails): String
    fun generateDriverToken(userDetails: UserDetails): String
    fun createToken(claims: Map<String, Any>, subject: String, millis: Int): String
    fun validateToken(token: String?, userDetails: UserDetails): Boolean
    fun isTokenExpired(token: String?): Boolean
    fun createToken(
        claims: Map<String, Any>,
        subject: String,
        date: Date
    ): String

    fun extractUsernameFromAuthorization(substring: String): String
    fun generateToken(username: String): String
}