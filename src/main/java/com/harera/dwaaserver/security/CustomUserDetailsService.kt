package com.harera.dwaaserver.security

import com.harera.dwaaserver.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails? {
        val user = userRepository.getUserWithUsername(username)
        return if (user.isPresent) {
            CustomUserDetails(user.get().username!!, user.get().password!!)
        } else {
            null
        }
    }
}